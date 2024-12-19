import React from 'react';
import {Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './redux/store';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import NavBar from './components/NavBar';
import RouteGuard from './components/RouteGuard';

const App: React.FC = () => {
    return (
        <Provider store={store}>
            <NavBar />
            <Routes>
                <Route path="/" element= {
                    <h1 className="center-heading middle-heading">Welcome to The Ticketing System</h1> 
                    } />    
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/dashboard" element={
                    <RouteGuard>
                        <Dashboard />
                    </RouteGuard>
                }/>
            </Routes>
        </Provider>
    );
};

export default App;