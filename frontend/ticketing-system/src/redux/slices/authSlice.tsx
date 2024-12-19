import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface Ticket {
    id: number,
    amount: number,
    description: string,
    status: string
}

interface AuthState {
    username: string | null;
    role: string | null;
    tickets: Ticket[] | null;
}


const initialState: AuthState = {
    username: localStorage.getItem('username'),
    role: localStorage.getItem('userRole'),
    tickets: null,
};

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<string>) => {
            state.username = action.payload;
            localStorage.setItem('username', action.payload);
        },
        setRole: (state, action: PayloadAction<string>) => {
            state.role = action.payload;
            localStorage.setItem('userRole', action.payload);
        },
        clearRole: (state) => {
            state.username = null;
            state.role = null;
            state.tickets = null;
            localStorage.removeItem('username');
            localStorage.removeItem('userRole');
        },
        setTickets: (state, action: PayloadAction<Ticket[]>) => {
            state.tickets = action.payload;
        },
    },
});

export const { setUser, setRole, clearRole, setTickets } = authSlice.actions;
export default authSlice.reducer;