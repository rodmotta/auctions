import { Bell, User } from "lucide-react"
import { useNavigate } from "react-router"
import { useAuth } from "../../hooks/useAuth"
import Button from "../shared/Button";

function Navbar() {

    const navigate = useNavigate();
    const { isAuthenticated } = useAuth();

    const notificationCount = 1

    return (
        <div className="border-b border-stone-300 py-2" >
            <div className="flex justify-between items-center max-w-6xl m-auto">
                <h1 className="text-xl font-semibold">Leilão Online</h1>
                {/* <input type="text" name="" id=""
                    className="border border-stone-300 rounded-lg px-4 py-2"
                    placeholder="Buscar leilões..." /> */}
                {isAuthenticated()
                    ?
                    <div className="flex gap-2">
                        <button className="relative hover:bg-stone-100 rounded-lg px-2 py-2 cursor-pointer">
                            <Bell />
                            {notificationCount > 0 &&
                                <span className="absolute -top-1 -right-1 bg-red-600 text-white text-xs font-bold px-1.5 py-0.5 rounded-full">
                                    {notificationCount}
                                </span>
                            }
                        </button>
                        <Button
                            variant='ghost'
                            className='px-2 py-2'
                            text={<User />}
                        />
                    </div>
                    :
                    <div className="flex gap-2">
                        <Button
                            variant='outlined'
                            className='px-4 py-2'
                            text='Entrar'
                            onClick={() => navigate('/login')}
                        />
                        <Button
                            variant='filled'
                            className='px-4 py-2'
                            text='Cadastrar'
                            onClick={() => navigate('/register')}
                        />
                    </div>
                }

            </div>
        </div>
    )
}

export default Navbar