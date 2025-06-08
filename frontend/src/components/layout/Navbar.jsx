import { Bell, LogOut } from "lucide-react"
import { getKeycloakLoginUrl } from "../../utils/auth";
import authStore from "../../stores/authStore";
import { useNavigate } from "react-router";

function Navbar() {
    const navigate = useNavigate();

    const isAuthenticated = authStore((state) => state.isAuthenticated);
    const { logout } = authStore();

    const handleLogout = () => {
        logout();
        navigate('/');
    }

    const notificationCount = 1

    return (
        <div className="border-b border-stone-300 py-2" >
            <div className="flex justify-between items-center max-w-6xl m-auto">
                <h1 className="text-xl font-semibold">Leilão Online</h1>
                {isAuthenticated
                    ?
                    <div className="flex gap-2">
                        <button className="btn btn-ghost rounded-lg px-2 py-2 relative">
                            <Bell />
                            {notificationCount > 0 &&
                                <span className="absolute -top-1 -right-1 bg-red-600 text-white text-xs font-bold px-1.5 py-0.5 rounded-full">
                                    {notificationCount}
                                </span>
                            }
                        </button>
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