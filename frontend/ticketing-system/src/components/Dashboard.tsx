import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import PendingTickets from './PendingTickets';
import TicketManager from './TicketManager';

const Dashboard: React.FC = () => {
    const navigate = useNavigate();
    const role = useSelector((state: RootState) => state.auth.role);

    useEffect(() => {
        if (!role) {
            navigate('/login'); // Redirect to login if no role is found
        }
    }, [role, navigate]);

    if (!role) {
        return null;
    }

    return (
        <div>
            {role === 'manager' && (
                <div>
                    <h1 className='center-heading'>Manager Dashboard</h1>
                    <PendingTickets />
                </div>
            )}
            {role === 'employee' && (
                <div>
                    <h1 className='center-heading'>Employee Dashboard</h1>
                    <TicketManager />
                </div>
            )}
        </div>
    );
};

export default Dashboard;