import { create } from "zustand";
import { exchangeCode, refreshAccessToken, invalidateRefreshToken } from "../service/userService";

const authStore = create((set, get) => {
    const isAuthenticated = !!localStorage.getItem("accessToken"); //converte em boleano

    return {
        isAuthenticated,

        login: async (code) => {
            try {
                const response = await exchangeCode(code);
                const { accessToken, refreshToken } = response.data;

                localStorage.setItem('accessToken', accessToken);
                localStorage.setItem('refreshToken', refreshToken);

                set({ isAuthenticated: true })

                return true;
            } catch (error) {
                console.error('Erro no login:', error);

                // localStorage.removeItem('accessToken');
                // localStorage.removeItem('refreshToken');

                // set({
                //     isAuthenticated: false,
                //     accessToken: null,
                //     refreshToken: null,
                // })

                return false;
            }
        },

        refreshToken: async () => {
            try {
                const storedRefreshToken = localStorage.getItem("refreshToken");

                if (!storedRefreshToken) {
                    throw new Error("Sem refresh token");
                }

                const response = await refreshAccessToken(storedRefreshToken);
                const { accessToken, refreshToken } = response.data;

                localStorage.setItem('accessToken', accessToken);
                localStorage.setItem('refreshToken', refreshToken);

                set({ isAuthenticated: true })

                return accessToken;
            } catch (error) {
                console.error("Erro ao renovar token:", error);
                await get().logout(); // força logout
                return null;
            }
        },

        logout: async () => {
            try {
                const storedRefreshToken = localStorage.getItem("refreshToken");

                if (storedRefreshToken) {
                    await invalidateRefreshToken(storedRefreshToken);
                }
            } catch (error) {
                console.error('Erro no logout do backend:', error);
            } finally {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');

                set({ isAuthenticated: false })
            }
        },
    };
});

export default authStore;