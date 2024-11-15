import React, { useState, useEffect } from 'react';
import {useNavigate} from 'react-router-dom';
import { register } from './api/auth';

function SignUp(){
  
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [pass, setPass] = useState('');
  const [repeatPass, setRepeatPass] = useState('');
  const [message, setMessage] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    if (pass !== repeatPass) {
      setMessage("Mật khẩu không khớp");
      setPass("");
      setRepeatPass("");
      return;
    }
    try {
      const response = await register(email, pass);
      setMessage(response.data);
      navigate(`/login`, {state: { successMessage: 'Đăng ký thành công. Vui lòng đăng nhập.'}});
    } catch (error) {
      setMessage(error.response.data);
    }
  }

  return (
    <div className='login-container'>
      <div className='login-form'>
        <form onSubmit={handleRegister}>
          <h3>Đăng ký</h3>
          <div className='login-email'>
            <label htmlFor='email'>Email</label> <br/>
            <input type='email' placeholder='Enter email' value={email} onChange={(e) => setEmail(e.target.value)}/>
          </div>
          <div className='login-pass'>
            <label htmlFor='password'>Mật khẩu</label> <br/>
            <input type='password' placeholder='Enter password' value={pass} onChange={(e) => setPass(e.target.value)}/>
          </div>
          <div className='login-pass-again'>
            <label htmlFor='password'>Nhập lại mật khẩu</label> <br/>
            <input type='password' placeholder='Enter password again' value={repeatPass} onChange={(e) => setRepeatPass(e.target.value)}/>
          </div>
          <p>{message}</p>
          <div className='login-btn'>
            <button type='submit'>Đăng ký</button>
          </div>
          <p>
            Đã có tài khoản
            <a href='/login'>Đăng nhập</a>
          </p>
        </form>
      </div>
    </div>
  );

}
export default SignUp