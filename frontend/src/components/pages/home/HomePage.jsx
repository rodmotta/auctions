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
      const response = await getAuctions();
      const auctionsData = response.data;
      setAuctions(auctionsData);
    } catch (error) {
      console.error("Erro ao buscar leilões:", error);
    }
  };

  return (
    <div className='flex flex-col gap-4'>
      <Navbar />
      <div className='max-w-6xl m-auto'>
        <div className='grid grid-cols-3 gap-4'>
          {auctions.map(auction => (
            <AuctionCard
              key={auction.id}
              auction={auction}
            />
          ))}
        </div>
      </div>
    </div>
  )
}

export default HomePage
