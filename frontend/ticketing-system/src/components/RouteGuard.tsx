import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';
import { RootState } from '../redux/store';

interface RouteGuardProps {
    children: React.ReactNode;
}

const RouteGuard: React.FC<RouteGuardProps> = ({ children }) => {
    const role = useSelector((state: RootState) => state.auth.role);
    console.log('Role:', role);

    if (!role) {
        return <Navigate to="/login" />;
    }

    return <>{children}</>;
};

export default RouteGuard;