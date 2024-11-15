import React, { useState, useEffect, useRef } from 'react';
import {useNavigate, useLocation} from 'react-router-dom';
import { login } from './api/auth';

function LoginRegister({setUser}){
  
  // state(true);
  
  // const handleLogin = () => {
  //   state(true);
  //   navigate(`/`);
  // };

  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [pass, setPass] = useState('');
  const [message, setMessage] = useState('');
  const location = useLocation();

  useEffect(() => {
    if (location.state && location.state.successMessage) {
      setMessage(location.state.successMessage);
    }
  }, [location]);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await login(email, pass);
      setMessage(response.data);
      setUser(email);
      navigate("/")
      // console.log("login ok");
    } catch (error) {
      setMessage(error.response.data);
    }
  };

  return (
    <div className='login-container'>
      <div className='login-form'>
        <form onSubmit={handleLogin}>
          <h3>Đăng nhập</h3>
          <div className='login-email'>
            <label htmlFor='email'>Email</label> <br/>
            <input type='email' placeholder='Enter email' value={email} onChange={(e) => setEmail(e.target.value)}/>
          </div>
          <div className='login-pass'>
            <label htmlFor='password'>Password</label> <br/>
            <input type='password' placeholder='Enter password' value={pass} onChange={(e) => setPass(e.target.value)}/>
          </div>
          <p>{message}</p>
          <div className='login-check'>
            <input type='checkbox' id='check'/>
            <label htmlFor='check'>
              Nhớ đăng nhập
            </label>
          </div>
          <div className='login-btn'>
            <button type='submit'>Đăng nhập</button>
          </div>
          <p>
            <a href='#' className='forgot-password'>Quên mật khẩu</a>
            <a href='/signup'>Đăng ký</a>
          </p>
        </form>
      </div>
    </div>
  );

}
export default LoginRegister