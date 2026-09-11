import React, {useEffect, useMemo, useState} from 'react'
import {createRoot} from 'react-dom/client'
import './style.css'

type Room={id:number,name:string,capacity:number,location:string,equipment:string}
type Booking={id:number,roomId:number,roomName:string,username:string,startAt:string,endAt:string,status:string}
type Availability={bookingId:number,roomName:string,startAt:string,endAt:string,status:string,username:string}
type Page='dashboard'|'rooms'|'book'|'mine'|'admin'
type Stats={totalRooms:number,totalUsers:number,totalBookings:number,activeBookings:number,cancelledBookings:number}

const API='http://localhost:8080'

function App(){
 const [page,setPage]=useState<Page>('dashboard')
 const [rooms,setRooms]=useState<Room[]>([])
 const [bookings,setBookings]=useState<Booking[]>([])
 const [login,setLogin]=useState('user')
 const [password,setPassword]=useState('user123')
 const [regUser,setRegUser]=useState('')
 const [regPassword,setRegPassword]=useState('')
 const [message,setMessage]=useState('')
 const [logged,setLogged]=useState(false)
 const [role,setRole]=useState('')
 const [selected,setSelected]=useState<Room|null>(null)
 const [start,setStart]=useState('2026-09-11T10:00')
 const [end,setEnd]=useState('2026-09-11T11:00')
 const [calendarDate,setCalendarDate]=useState('2026-09-11')
 const [availability,setAvailability]=useState<Availability[]>([])
 const [stats,setStats]=useState<Stats|null>(null)
 const [adminBookings,setAdminBookings]=useState<Booking[]>([])
 const [roomSearch,setRoomSearch]=useState('')
 const [minCapacity,setMinCapacity]=useState('')
 const [token,setToken]=useState(sessionStorage.getItem('access_token')||'')

 const isAdmin=role==='ROLE_ADMIN'

 async function api(path:string,opts:RequestInit={}){
   try{
     return await fetch(API+path,{...opts,headers:{...(token?{Authorization:'Bearer '+token}:{}),'Content-Type':'application/json',...(opts.headers||{})}})
   }catch{
     setMessage('Ошибка связи с сервером: backend недоступен или браузер заблокировал запрос')
     throw new Error('network')
   }
 }

 async function loadAdmin(){
   if(!isAdmin)return
   const [s,b]=await Promise.all([api('/api/admin/stats'),api('/api/admin/bookings')])
   if(s.ok)setStats(await s.json())
   if(b.ok)setAdminBookings(await b.json())
 }

 async function load(){
   const r=await api('/api/rooms')
   if(r.ok)setRooms(await r.json())
   else{setRooms([]);setMessage(`Ошибка загрузки комнат: ${r.status}`)}
   if(logged){
     const b=await api('/api/bookings/mine')
     if(b.ok)setBookings(await b.json())
     else setMessage(`Ошибка загрузки бронирований: ${b.status}`)
   }
 }

 async function enter(){
   try{
     const r=await fetch(API+'/api/auth/login',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({username:login,password})})
     const body=await r.json().catch(()=>({}))
     if(!r.ok){setMessage(body.error||'Неверные учетные данные');return}
     sessionStorage.setItem('access_token',body.token); setToken(body.token); setLogged(true); setRole(body.role); setLogin(body.username); setMessage('Вход выполнен');
   }catch{setMessage('Ошибка связи с сервером')}
 }

 async function register(){
   const r=await fetch(API+'/api/auth/register',{
     method:'POST',
     headers:{'Content-Type':'application/json'},
     body:JSON.stringify({username:regUser,password:regPassword})
   })
   if(r.ok){
     setLogin(regUser)
     setPassword(regPassword)
     setRegUser('')
     setRegPassword('')
     setMessage('Регистрация выполнена. Нажмите «Войти».')
   }else{
     const body=await r.json().catch(()=>({error:'Ошибка регистрации'}))
     setMessage(body.error||'Ошибка регистрации')
   }
 }

 async function checkAvailability(){
   if(!selected)return setMessage('Выберите комнату')
   const r=await api(`/api/bookings/availability?roomId=${selected.id}&date=${calendarDate}`)
   if(r.ok)setAvailability(await r.json())
   else setMessage(`Ошибка календаря: ${r.status}`)
 }

 async function book(){
   if(!selected)return setMessage('Сначала выберите комнату')
   const r=await api('/api/bookings',{
     method:'POST',
     body:JSON.stringify({roomId:selected.id,startAt:start,endAt:end})
   })
   const body=await r.json().catch(()=>({}))
   setMessage(r.ok?'Бронирование создано':(body.error||'Ошибка бронирования'))
   await load()
   await checkAvailability()
 }

 async function cancel(id:number){
   const r=await api('/api/bookings/'+id,{method:'DELETE'})
   setMessage(r.ok?'Бронирование отменено':'Ошибка отмены')
   await load()
 }

 async function addRoom(){
   const name=prompt('Название комнаты'); if(!name)return
   const cap=Number(prompt('Вместимость','8'))
   if(!Number.isFinite(cap)||cap<1)return setMessage('Некорректная вместимость')
   const location=prompt('Расположение','1 этаж')||'1 этаж'
   const equipment=prompt('Оборудование','Проектор')||''
   const r=await api('/api/admin/rooms',{method:'POST',body:JSON.stringify({name,capacity:cap,location,equipment})})
   setMessage(r.ok?'Комната создана':'Ошибка создания')
   await load()
   await loadAdmin()
 }

 async function editRoom(room:Room){
   const name=prompt('Название',room.name)||room.name
   const cap=Number(prompt('Вместимость',String(room.capacity)))
   const location=prompt('Расположение',room.location)||room.location
   const equipment=prompt('Оборудование',room.equipment)||room.equipment
   const r=await api('/api/admin/rooms/'+room.id,{method:'PUT',body:JSON.stringify({name,capacity:cap,location,equipment})})
   setMessage(r.ok?'Комната обновлена':'Ошибка обновления')
   await load()
   await loadAdmin()
 }

 async function deleteRoom(id:number){
   if(!confirm('Удалить комнату?'))return
   const r=await api('/api/admin/rooms/'+id,{method:'DELETE'})
   setMessage(r.ok?'Комната удалена':'Нельзя удалить комнату с зависимыми бронированиями')
   await load()
   await loadAdmin()
 }

 function changeLogin(value:string){setLogin(value);if(logged){sessionStorage.removeItem('access_token');setToken('');setLogged(false);setRole('');setPage('dashboard');setMessage('Введите учетные данные и нажмите «Войти»')}}
 function changePassword(value:string){setPassword(value);if(logged){sessionStorage.removeItem('access_token');setToken('');setLogged(false);setRole('');setPage('dashboard');setMessage('Введите учетные данные и нажмите «Войти»')}}

 useEffect(()=>{if(logged){load();if(isAdmin)loadAdmin()}},[logged,role])
 useEffect(()=>{if(page==='book'&&selected)checkAvailability()},[page,selected,calendarDate])

 const filteredRooms=useMemo(()=>rooms.filter(r=>{
   const q=roomSearch.trim().toLowerCase()
   const okName=!q||`${r.name} ${r.location} ${r.equipment}`.toLowerCase().includes(q)
   const okCap=!minCapacity||r.capacity>=Number(minCapacity)
   return okName&&okCap
 }),[rooms,roomSearch,minCapacity])

 const navItems:[Page,string][]=[['dashboard','Главная'],['rooms','Комнаты'],['book','Бронирование'],['mine','Мои бронирования']]

 return <>
  <header>
    <div><h1>WorkSpace Booking</h1><span>Управление переговорными комнатами</span></div>
    <div className="auth">
      <input value={login} onChange={e=>changeLogin(e.target.value)} placeholder="Логин" autoComplete="username"/>
      <input value={password} onChange={e=>changePassword(e.target.value)} type="password" placeholder="Пароль" autoComplete="current-password"/>
      <button onClick={enter}>{logged?'Войти снова':'Войти'}</button>
    </div>
  </header>

  {logged&&<nav>
    {navItems.map(([k,v])=><button key={k} onClick={()=>setPage(k)}>{v}</button>)}
    {isAdmin&&<button onClick={()=>setPage('admin')}>Администрирование</button>}
  </nav>}

  <main>
   {!logged?
    <div className="auth-grid">
      <section className="hero"><h2>Сервис бронирования рабочих пространств</h2><p>Быстрый поиск, бронирование и контроль переговорных комнат.</p><p>Демо: <b>user / user123</b> или <b>admin / admin123</b></p></section>
      <section className="card"><h2>Регистрация</h2>
        <input value={regUser} onChange={e=>setRegUser(e.target.value)} placeholder="Новый логин"/>
        <input value={regPassword} onChange={e=>setRegPassword(e.target.value)} type="password" placeholder="Пароль от 6 символов"/>
        <button onClick={register}>Зарегистрироваться</button>
      </section>
    </div>
   :
    page==='dashboard'?
      <section className="card"><h2>Главная</h2><p>Добро пожаловать, <b>{login}</b>.</p>
        <div className="grid">
          <div className="stat"><b>{rooms.length}</b><span>доступных комнат</span></div>
          <div className="stat"><b>{bookings.filter(b=>b.status==='ACTIVE').length}</b><span>моих активных броней</span></div>
        </div>
      </section>
   :
    page==='rooms'?
      <section><div className="section-head"><h2>Переговорные</h2><div className="filters"><input value={roomSearch} onChange={e=>setRoomSearch(e.target.value)} placeholder="Поиск по названию, этажу, оборудованию"/><input value={minCapacity} onChange={e=>setMinCapacity(e.target.value)} type="number" min="1" placeholder="От мест"/></div></div>
        <div className="grid">{filteredRooms.map(r=><article className={'room '+(selected?.id===r.id?'selected':'')} onClick={()=>setSelected(r)} key={r.id}><h3>{r.name}</h3><p>Вместимость: {r.capacity}</p><p>{r.location}</p><p>{r.equipment}</p><button onClick={(e)=>{e.stopPropagation();setSelected(r);setPage('book')}}>Забронировать</button></article>)}</div>
        {filteredRooms.length===0&&<div className="card"><p>Подходящих комнат не найдено.</p></div>}
      </section>
   :
    page==='book'?
      <section className="card">
        <h2>Новое бронирование</h2>
        <label>Комната<select value={selected?.id||''} onChange={e=>setSelected(rooms.find(x=>x.id===Number(e.target.value))||null)}><option value="">Выберите</option>{rooms.map(r=><option key={r.id} value={r.id}>{r.name} · {r.capacity} мест</option>)}</select></label>
        <div className="two-col">
          <label>Начало<input type="datetime-local" value={start} onChange={e=>setStart(e.target.value)}/></label>
          <label>Окончание<input type="datetime-local" value={end} onChange={e=>setEnd(e.target.value)}/></label>
        </div>
        <button onClick={book}>Забронировать</button>
        <div className="calendar">
          <h3>Занятость комнаты</h3>
          <label>Дата<input type="date" value={calendarDate} onChange={e=>setCalendarDate(e.target.value)}/></label>
          {availability.length===0?<p>На выбранную дату занятых интервалов не найдено.</p>:availability.map(a=><div className="booking" key={a.bookingId}><b>{a.roomName}</b> — {new Date(a.startAt).toLocaleTimeString([], {hour:'2-digit',minute:'2-digit'})}–{new Date(a.endAt).toLocaleTimeString([], {hour:'2-digit',minute:'2-digit'})} · {a.username}</div>)}
        </div>
      </section>
   :
    page==='mine'?
      <section className="card"><h2>Мои бронирования</h2>{bookings.length===0?<p>Пока нет записей.</p>:bookings.map(b=><div className="booking" key={b.id}><div><b>{b.roomName}</b><br/>{new Date(b.startAt).toLocaleString()} — {new Date(b.endAt).toLocaleString()}<br/><small>{b.status}</small></div>{b.status==='ACTIVE'&&<button onClick={()=>cancel(b.id)}>Отменить</button>}</div>)}</section>
   :
      <section className="card">
        <h2>Администрирование</h2>
        <div className="grid">
          <div className="stat"><b>{stats?.totalRooms??'—'}</b><span>комнат</span></div>
          <div className="stat"><b>{stats?.totalUsers??'—'}</b><span>пользователей</span></div>
          <div className="stat"><b>{stats?.activeBookings??'—'}</b><span>активных броней</span></div>
          <div className="stat"><b>{stats?.cancelledBookings??'—'}</b><span>отменённых броней</span></div>
        </div>
        <button onClick={addRoom}>Добавить комнату</button>
        <h3>Управление комнатами</h3>
        {rooms.map(r=><div className="booking" key={r.id}><div><b>{r.name}</b> · {r.capacity} мест · {r.location}<br/><small>{r.equipment}</small></div><div className="actions"><button onClick={()=>editRoom(r)}>Изменить</button><button onClick={()=>deleteRoom(r.id)}>Удалить</button></div></div>)}
        <h3>Последние бронирования</h3>
        {adminBookings.length===0?<p>Записей пока нет.</p>:adminBookings.slice(0,10).map(b=><div className="booking" key={b.id}><b>{b.roomName}</b> — {b.username} — {new Date(b.startAt).toLocaleString()} — {b.status}</div>)}
      </section>}
  </main>
  <footer>{message}</footer>
 </>
}

createRoot(document.getElementById('root')!).render(<React.StrictMode><App/></React.StrictMode>)
