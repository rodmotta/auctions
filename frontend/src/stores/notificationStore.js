import { create } from "zustand";
import { getUserNotifications } from "../service/notificationService";


const useNotificationStore = create((set, get) => ({
    notifications: [],
    loading: true,

    fetchNotifications: async () => {
        set({ loading: true });
        try {
            const response = await getUserNotifications();
            set({ notifications: response.data });
        } catch (error) {
            console.error("Erro ao buscar notificações:", error);
        } finally {
            set({ loading: false });
        }
    },
    
    setNotifications: (data) => set({ notifications: data }),
}));

export default useNotificationStore;