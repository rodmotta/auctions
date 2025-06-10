import { Bell, LogOut } from "lucide-react"
import { getKeycloakLoginUrl } from "../../utils/auth";
import authStore from "../../stores/authStore";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { getUserNotifications } from "../../service/notificationService";
import { formatCurrencyBR, formatDateTimeBR } from "../../utils/formatterUtils";

function Navbar() {
    const navigate = useNavigate();

    const isAuthenticated = authStore((state) => state.isAuthenticated);
    const { logout } = authStore();

    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        fetchUserNotifications();
    }, [])

    const fetchUserNotifications = async () => {
        try {
            const response = await getUserNotifications();
            const notificationsData = response.data;
            setNotifications(notificationsData);
        } catch (error) {
            console.error("Erro ao buscar notificacoes:", error);
        }
    };

    const handleLogout = () => {
        logout();
        navigate('/');
    }

    return (
        <div className="border-b border-stone-300 py-2" >
            <div className="flex justify-between items-center max-w-6xl m-auto">
                <h1 className="text-xl font-semibold">Leilão Online</h1>
                {isAuthenticated
                    ?
                    <div className="flex gap-2">

                        <div className="dropdown dropdown-end">
                            <div tabIndex={0} className="btn btn-ghost rounded-lg px-2 py-2 relative">
                                <Bell />
                                {notifications.length > 0 && (
                                    <span className="absolute -top-1 -right-1 bg-red-600 text-white text-xs font-bold px-1.5 py-0.5 rounded-full">
                                        {notifications.length}
                                    </span>
                                )}
                            </div>
                            <div tabIndex={0} className="mt-3 z-1 card dropdown-content w-80 bg-base-100 shadow">
                                <div className="card-body p-4">
                                    <h3 className="font-semibold text-lg">Notificações</h3>
                                    {notifications.length === 0
                                        ? (
                                            <div className="flex flex-col items-center justify-center text-stone-400 py-6">
                                                <Bell className="w-12 h-12" />
                                                <p>Nenhuma notificação</p>
                                            </div>
                                        ) : (
                                            <div className="flex flex-col gap-2">
                                                {notifications.map(notification => (
                                                    <div className="p-4 hover:bg-gray-50 rounded-lg cursor-pointer" onClick={() => navigate(`/auction/${notification.auctionId}`)}>
                                                        <div className="flex items-start gap-2">
                                                            <span className="text-xl">🔨</span>
                                                            <div className="flex-1">
                                                                <p className="font-semibold">{`Você foi superado por um novo lance de R$ ${formatCurrencyBR(notification.bidAmount)}`}</p>
                                                                <div className="flex justify-between items-center mt-1 text-xs text-gray-400">
                                                                    <span>{notification.auctionTitle}</span>
                                                                    <span>{formatDateTimeBR(notification.placedAt)}</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                ))}
                                            </div>
                                        )
                                    }

                                </div>
                            </div>
                        </div>

                        <button
                            className="btn btn-ghost rounded-lg px-2 py-2"
                            onClick={handleLogout}
                        >
                            <LogOut />
                        </button>
                    </div>
                    :
                    <div className="flex gap-2">
                        <button
                            className="btn btn-neutral rounded-lg"
                            onClick={getKeycloakLoginUrl}
                        >
                            Entrar
                        </button>
                    </div>
                }

            </div>
        </div>
    )
}

export default Navbar