import { useEffect, useState } from "react";
import AuctionCard from "../../pages/home/AuctionCard";
import { getAuctions } from "../../../service/auctionService";
import Navbar from "../../layout/Navbar";

function HomePage() {

  const [loading, setLoading] = useState(true);
  const [status, setStatus] = useState();
  const [auctions, setAuctions] = useState([]);

  useEffect(() => {
    const fetchAuctions = async () => {
      try {
        const response = await getAuctions(status);
        setAuctions(response.data);
      } catch (error) {
        console.error("Erro ao buscar leilões:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchAuctions();
  }, [status]);

  return (
    <div className="flex flex-col">
      <Navbar />
      <div className="max-w-6xl m-auto p-4">
        {loading
          ? (
            <div>
              <span className="loading loading-spinner loading-xl"></span>
            </div>
          ) : (
            <div className="flex flex-col gap-4">

              <div>
                <select defaultValue="ACTIVE" className="select" onChange={(e) => setStatus(e.target.value)}>
                  <option value="PENDING">Nao Iniciado</option>
                  <option value="ACTIVE">Ativo</option>
                  <option value="COMPLETED">Finalizado</option>
                </select>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {auctions.map(auction => (
                  <AuctionCard
                    key={auction.id}
                    auction={auction}
                  />
                ))}
              </div>
            </div>
          )}
      </div>
    </div>
  )
}

export default HomePage
