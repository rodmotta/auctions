import { createBrowserRouter, RouterProvider } from "react-router";
import HomePage from "./components/pages/home/HomePage";
import RegisterPage from "./components/pages/auth/RegisterPage";
import LoginPage from "./components/pages/auth/LoginPage";
import AuctionDetailsPage from "./components/pages/auction/AuctionDetailsPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />,
  },
  {
    path: "/register",
    element: <RegisterPage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/auction/:id",
    element: <AuctionDetailsPage />,
  }
]);

function App() {
  return (
    <RouterProvider router={router} />
  )
}

export default App
