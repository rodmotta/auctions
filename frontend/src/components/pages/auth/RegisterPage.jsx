import { useForm } from "react-hook-form";
import { useNavigate } from "react-router";
import { register as registerUser } from "../../../service/userService";
import Button from "../../shared/Button";
import InputField from "../../shared/InputField";
import BorderBox from "../../shared/BorderBox";

function RegisterPage() {
    const navigate = useNavigate();
    const { register, handleSubmit } = useForm();

    const onSubmit = async (data) => {
        try {
            await registerUser(data);
            navigate("/login");
        } catch (error) {
            console.error('Erro:', error);
        }
    };

    return (
        <div className="h-screen flex justify-center items-center">
            <BorderBox
                className="w-full max-w-md"
            >
                <form
                    onSubmit={handleSubmit(onSubmit)}
                    className="flex flex-col gap-4"
                >
                    <h1 className="text-2xl font-semibold">Criar nova conta</h1>

                    <InputField
                        label="Nome"
                        type="text"
                        name="name"
                        required
                        {...register("name")}
                    />

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
                        name="password"
                        required
                        placeholder="***********"
                        {...register("password")}
                    />

                    <Button
                        variant='filled'
                        className='px-4 py-2'
                        type="submit"
                        text='Criar conta'
                    />
                </form>
            </BorderBox>
        </div>
    )
}

export default RegisterPage