import { useForm } from "react-hook-form";
import { login as loginUser } from '../../../service/userService'
import { useNavigate } from "react-router";
import { useAuth } from "../../../hooks/useAuth";
import Button from "../../shared/Button";
import InputField from "../../shared/InputField";

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
            <div className="w-full max-w-md border border-stone-300 rounded-lg p-4">
                <form
                    onSubmit={handleSubmit(onSubmit)}
                    className="flex flex-col gap-4"
                >
                    <h1 className="text-2xl font-semibold">Entrar na sua conta</h1>

                    <InputField
                        label="E-mail"
                        type="email"
                        name="email"
                        required
                        placeholder="seu@email.com"
                        {...register("email")}
                    />

                    <InputField
                        label="Senha"
                        type="password"
                        name="senha"
                        required
                        placeholder="***********"
                        {...register("password")}
                    />

                    <Button
                        variant='filled'
                        className='px-4 py-2'
                        type="submit"
                        text='Entrar'
                    />
                </form>
            </div>
        </div>
    )
}

export default LoginPage