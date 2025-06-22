import { Bell } from "lucide-react"
import { useNavigate } from "react-router";
import { formatCurrencyBR, formatDateTimeBR } from "../../utils/formatterUtils";
import { useEffect } from "react";
import useNotificationStore from "../../stores/notificationStore";

function NotificationDropdown() {
    const navigate = useNavigate();
    const { notifications, loading, fetchNotifications } = useNotificationStore();

    useEffect(() => {
        if (notifications.length === 0) {
            fetchNotifications();
        }
    }, []);

    return (
        <div className="dropdown dropdown-end">
            <div
                tabIndex={0}
                className="btn btn-ghost rounded-lg px-2 py-2 relative"
            >
                <Bell />
                {notifications.length > 0 && (
                    <span className="absolute -top-1 -right-1 bg-red-600 text-white text-xs font-bold px-1.5 py-0.5 rounded-full">
                        {notifications.length}
                    </span>
                )}
            </div>
            <div
                tabIndex={0}
                className="mt-3 z-1 card dropdown-content w-80 bg-base-100 shadow"
            >
                <div className="card-body p-4">
                    <h3 className="font-semibold text-lg">Notificações</h3>
                    {loading
                        ? (
                            <div className="flex justify-center">
                                <span className="loading loading-spinner loading-xl"></span>
                            </div>
                        ) : notifications.length === 0
                            ? (
                                <div className="flex flex-col items-center justify-center text-stone-400 py-6">
                                    <Bell className="w-12 h-12" />
                                    <p>Nenhuma notificação</p>
                                </div>
                            ) : (
                                <div className="flex flex-col gap-2">
                                    {notifications.map(notification => (
                                        <div
                                            key={notification.id}
                                            className="p-4 bg-base-200 rounded-lg cursor-pointer"
                                            onClick={() => navigate(`/auction/${notification.auctionId}`)}
                                        >
                                            {notification.type === "WINNER" ? (
                                                <div className="flex items-start gap-2">
                                                    <span className="text-xl">🥇</span>
                                                    <div className="flex-1">
                                                        <p className="font-semibold">{`Você venceu o leilão!`}</p>
                                                        <div className="flex justify-between items-center mt-1 text-xs text-gray-400">
                                                            <span>{notification.auctionTitle}</span>
                                                            <span>{formatDateTimeBR(notification.placedAt)}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            ) : (
                                                <div className="flex items-start gap-2">
                                                    <span className="text-xl">🔨</span>
                                                    <div className="flex-1">
                                                        {/* <p className="font-semibold">{`Você foi superado por um novo lance de R$ ${formatCurrencyBR(notification.bidAmount)}`}</p> */}
                                                        <div className="flex justify-between items-center mt-1 text-xs text-gray-400">
                                                            <span>{notification.auctionTitle}</span>
                                                            <span>{formatDateTimeBR(notification.placedAt)}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            )}
                                        </div>
                                    ))}
                                </div>
                            )
                    }
                </div>
            </div>
        </div>
    )
}

export default NotificationDropdown