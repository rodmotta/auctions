import { useEffect, useRef } from 'react';
import { useLocation, useNavigate } from "react-router";
import authStore from '../../../stores/authStore';

function AuthCallbackPage() {
    const location = useLocation();
    const navigate = useNavigate();

    const isAuthenticated = authStore((state) => state.isAuthenticated);
    const { login } = authStore();

    useEffect(() => {
        if (isAuthenticated) {
            navigate('/');
            return;
        }

        const urlParams = new URLSearchParams(location.search);
        const code = urlParams.get('code');

        if (code) {
            login(code);
            navigate('/');
        } else {
            console.error("Nenhum código de autenticação encontrado na URL.");
        }
    }, []);

    return (
        <div>
            Processando autenticação...
        </div>
    )
}

export default AuthCallbackPage