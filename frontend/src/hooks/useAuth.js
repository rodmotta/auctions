export function useAuth() {

    const login = (response) => {
        localStorage.setItem("accessToken", response.accessToken);
        localStorage.setItem("expiresAt", response.expiresAt);
    }

    const isAuthenticated = () => {
        const accessToken = localStorage.getItem("accessToken");
        const expiresAt = localStorage.getItem("expiresAt");

        if (!accessToken || !expiresAt) {
            return false;
        }

        const now = new Date().getTime();
        const expiry = Date.parse(expiresAt);

        if (isNaN(expiry) || now > expiry) {
            return false;
        }

        return true;
    }

    return { login, isAuthenticated };
};