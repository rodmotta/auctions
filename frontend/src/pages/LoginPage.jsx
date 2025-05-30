import { useForm } from "react-hook-form";
import { login as loginUser } from '../service/userService'
import { useNavigate } from "react-router";
import { useAuth } from "../hooks/useAuth";

function LoginPage() {
    const navigate = useNavigate();
    const { login } = useAuth();
    const { register, handleSubmit } = useForm();


    const onSubmit = async (data) => {
        try {
            const response = await loginUser(data);
            login(response)
            navigate("/");
        } catch (error) {
            console.error('Erro:', error);
        }
    };

    return (
        <div className="h-screen flex justify-center items-center">
            <div className="w-full max-w-md justify-center border border-stone-300 rounded-lg p-4">
                <h1 className="text-2xl font-semibold">Entrar na sua conta</h1>
                <form
                    onSubmit={handleSubmit(onSubmit)}
                    className="flex flex-col"
                >
                    <label className="mt-4 text-stone-500">E-mail</label>
                    <input
                        type="text"
                        className="border border-stone-300 rounded-lg py-2 px-4"
                        required
                        placeholder="seu@email.com"
                        {...register("email")}
                    />

                    <label className="mt-4 text-stone-500">Password</label>
                    <input
                        type="text"
                        className="border border-stone-300 rounded-lg py-2 px-4"
                        required
                        placeholder="***********"
                        {...register("password")}
                    />

                    <button
                        type="submit"
                        className="bg-black text-white font-semibold rounded-lg px-4 py-2 mt-4 cursor-pointer"
                    >
                        Entrar
                    </button>
                </form>
            </div>
        </div>
    )
}

export default LoginPage