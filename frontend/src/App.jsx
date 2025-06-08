import { createBrowserRouter, RouterProvider } from "react-router";
import HomePage from "./components/pages/home/HomePage";
import AuthCallbackPage from "./components/pages/auth/AuthCallbackPage";
import AuctionDetailPage from "./components/pages/auction/AuctionDetailPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />,
  },
  {
    path: "/callback",
    element: <AuthCallbackPage />,
  },
  {
    path: "/auction/:id",
    element: <AuctionDetailPage />,
  }
]);

function App() {
  return (
    <RouterProvider router={router} />
  )
}

export default App
