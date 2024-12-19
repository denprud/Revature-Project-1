// filepath: /c:/Users/denze/Revature-Project-1/frontend/ticketing-system/src/components/TicketList.tsx
import React from 'react';
import  Ticket  from '../types/Ticket';

interface TicketListProps {
    tickets: Ticket[];
}

const TicketList: React.FC<TicketListProps> = ({ tickets }) => {
    return (
        <div>
            <h1 className='center-heading'>Ticket History</h1>
            {tickets.length === 0 ? (
                <p className='center-heading'>No ticket history</p>
            ) : (
                <ul>
                    {tickets.map(ticket => (
                        <li key={ticket.id}>
                            <p>Amount: {ticket.amount}</p>
                            <p>Description: {ticket.description}</p>
                            <p>Status: {ticket.status}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default TicketList;