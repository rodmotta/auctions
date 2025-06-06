import { createBrowserRouter, RouterProvider } from "react-router";
import HomePage from "./components/pages/home/HomePage";
import AuctionDetailsPage from "./components/pages/auction/AuctionDetailsPage";
import AuthCallbackPage from "./components/pages/auth/AuthCallbackPage";

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
    element: <AuctionDetailsPage />,
  }
]);

function App() {
  return (
    <RouterProvider router={router} />
  )
}

export default App
