import React, { useState } from 'react';

interface SubmitTicketProps {
    onSubmit: (amount: number, description: string) => void;
}

const SubmitTicket: React.FC<SubmitTicketProps> = ({ onSubmit }) => {
    const [amount, setAmount] = useState<number>(0);
    const [description, setDescription] = useState<string>("");

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onSubmit(amount, description);
    };

    return (
        <form className="sub-form form-outline" onSubmit={handleSubmit}>
            <caption>Submit a ticket</caption>
            <div>
                <label htmlFor="amount">Amount:</label>
                <input
                    type="number"
                    id="amount"
                    value={amount}
                    onChange={(e) => setAmount(Number(e.target.value))}
                />
            </div>
            <div>
                <label htmlFor="description">Description:</label>
                <input
                    type="text"
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />
            </div>
            <button type="submit">Submit</button>
        </form>
    );
};

export default SubmitTicket;