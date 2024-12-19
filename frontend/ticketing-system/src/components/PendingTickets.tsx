import React, {useEffect, useState} from 'react'
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import axios from 'axios';
import Ticket from '../types/Ticket';


function PendingTickets(): React.JSX.Element {
    const [tickets, setTickets] = useState<Ticket[]>([])
    const username = useSelector((state: RootState) => state.auth.username);

    useEffect(() => {

        async function fetchTickets(){
            try{
                const response = await axios.get(`http://localhost:8080/api/tickets/pending?username=${username}`);
                setTickets(response.data)
            } catch (error: unknown) {
                console.error('Failed to fetch tickets', error);
            }
        }

        fetchTickets();
    }, [tickets, username]);


    const handleApprove = async (ticketID: number) => {
        try{
            const status = "Approved";
            const response = await axios.post(`http://localhost:8080/api/tickets/process?username=${username}&ticketId=${ticketID}&status=${status}`);
            console.log(response.data);
        } catch(error: unknown){
            console.error('Failed to approve ticket', error);
        }   
    }

    const handleDeny = async (ticketID: number) => {
        try{
            const status = "Denied";
            const response = await axios.post(`http://localhost:8080/api/tickets/process?username=${username}&ticketId=${ticketID}&status=${status}`);
            console.log(response.data);
        } catch(error: unknown){
            console.error('Failed to deny ticket', error);
        }
    }
    

  return (
        <>
            <h1 className='center-heading'> Pending Tickets </h1>
            {tickets.length === 0 ? (
                <p className='center-heading'>No pending tickets</p>
            ) : (
            <ul>
                {tickets.map((ticket) => (
                    <li key={ticket.id} className='ticket'>
                        <div className='ticket-desc'>
                            <p>Amount: {ticket.amount}</p>
                            <p>Description: {ticket.description}</p>
                            <p>Status: {ticket.status}</p>
                        </div>
                        <div className='horizontal-button-div'>
                            <button onClick={() => handleApprove(ticket.id)}>Approve</button>
                            <button onClick={() => handleDeny(ticket.id)}>Deny</button>
                        </div>
                    </li>
                ))}
            </ul>
        )}
        </>
  )
}

export default PendingTickets