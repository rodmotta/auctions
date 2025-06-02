import { useEffect, useState } from 'react';
import AuctionCard from '../../pages/home/AuctionCard';
import { getAuctions } from '../../../service/auctionService';
import Navbar from '../../layout/Navbar';

function HomePage() {
  const [auctions, setAuctions] = useState([]);

  useEffect(() => {
    fetchAuctions();
  }, []);

  const fetchAuctions = async () => {
    try {
      const auctionsData = await getAuctions(); // Assumindo que é async
      setAuctions(auctionsData);
    } catch (error) {
      console.error("Erro ao buscar leilões:", error);
      setAuctions([]); // Fallback para array vazio
    }
  };

  return (
    <>
      <Navbar/>
      <div className='max-w-6xl m-auto'>  
        <h1 className='text-2xl font-bold my-4'>Leilões Ativos</h1>
        <div className='grid grid-cols-4 gap-4'>
          {auctions.map(auction => (
            <AuctionCard
              key={auction.id}
              auction={auction}
            />
          ))}
        </div>

      </div>
    </>
  )
}

export default HomePage
