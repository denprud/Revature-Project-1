import React, { useState, FormEvent } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setRole, setUser } from '../redux/slices/authSlice';

const Login: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleSubmit = async (e: FormEvent) => {
        console.log('Login form submitted');
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/users/login', { username, password });
            console.log(response.data);
            const userRole = response.data.role;
            const userName = response.data.username;
            dispatch(setRole(userRole));
            dispatch(setUser(userName));
            navigate('/dashboard');
        } catch (error) {
            console.error('Login failed', error);
        }
    };

    return (
        <div className='signup'>
            <form onSubmit={handleSubmit}>
                <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Username" />
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;