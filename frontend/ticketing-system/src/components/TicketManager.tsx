import React, {useEffect } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { setTickets } from '../redux/slices/authSlice';
import SubmitTicket from './SubmitTicket';
import TicketList from './TicketList';

const TicketManager: React.FC = () => {
    // const [tickets, setTickets] = useState([]);
    const tickets = useSelector((state: RootState) => state.auth.tickets) || [];
    const username = useSelector((state: RootState) => state.auth.username);
    const dispatch = useDispatch();

    useEffect(() => {
        async function fetchTickets() {
            try {
                const response = await axios.get(`http://localhost:8080/api/tickets/user?username=${username}`);
                dispatch(setTickets(response.data));
            } catch (error: unknown) {
                console.error('Failed to fetch tickets', error);
            }
        }
        fetchTickets();
    }, [username, dispatch]);

    const handleTicketSubmit = async (amount: number, description: string) => {
        try {
            await axios.post(`http://localhost:8080/api/tickets/submit?username=${username}`, { amount, description });
            console.log('Ticket submitted');
            // Fetch the updated list of tickets after submission
            const response = await axios.get(`http://localhost:8080/api/tickets/user?username=${username}`);
            dispatch(setTickets(response.data));
        } catch (error: unknown) {
            console.error('Ticket submission failed', error);
        }
    };

    return (
        <div>
            <SubmitTicket onSubmit={handleTicketSubmit} />
            <TicketList tickets={tickets} />
        </div>
    );
};

export default TicketManager;