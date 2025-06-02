import { formatCurrencyBR, formatTimeRemaining } from '../../../utils/formatterUtils';
import { Banknote, Clock, User } from "lucide-react";
import { useParams } from "react-router";
import { getAuctionsById } from "../../../service/auctionService";
import { useEffect, useState } from "react";
import Navbar from "../../layout/Navbar";
import BidsHistoryCard from './BidsHistoryCard';
import PlaceBidForm from './PlaceBidForm';
import BorderBox from '../../shared/BorderBox';
import { Client } from '@stomp/stompjs';
import { getBidsByAuctionId } from '../../../service/bidService';

function AuctionDetailsPage() {

    const { id } = useParams();
    const [auction, setAuction] = useState(null);
    const [bids, setBids] = useState([]);

    useEffect(() => {
        fetchAuctionById();
        fetchBidsByAuctionId();
        connectWebSocket();
    }, [id]);

    const fetchAuctionById = async () => {
        try {
            const auctionData = await getAuctionsById(id);
            setAuction(auctionData);
        } catch (error) {
            console.error("Erro ao buscar leilões:", error);
        }
    };

    const fetchBidsByAuctionId = async () => {
        try {
            const bidsData = await getBidsByAuctionId(id);
            setBids(bidsData);
        } catch (error) {
            console.error("Erro ao buscar lances:", error);
        }
    };

    const connectWebSocket = () => {
        const stompClient = new Client({
            brokerURL: 'ws://localhost:8082/ws',
            reconnectDelay: 5000,
            onConnect: () => {
                stompClient.subscribe(`/topic/auction/${id}/bids`, message => {
                    const bids = JSON.parse(message.body);
                    setBids(bids);
                });
            },
        })

        stompClient.activate();
        return () => stompClient.deactivate();
    }

    if (!auction || !bids) {
        return (
            <div>Carregando...</div>
        )
    }

    return (
        <div>
            <Navbar />
            <div className='max-w-6xl m-auto mt-6'>
                <div className="grid grid-cols-2 gap-4">
                    <figure>
                        <img
                            className='rounded-lg'
                            src="https://images.unsplash.com/photo-1506610654-064fbba4780c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt=""
                        />
                    </figure>
                    <div>
                        <h1 className="text-3xl font-bold">{auction.title}</h1>
                        <div className="flex items-center my-4 text-stone-500">
                            <Clock className='mr-2 h-4 w-4' />
                            {formatTimeRemaining(auction.endTime)}
                        </div>
                        <BorderBox>
                            <p className="text-stone-500">Lance atual</p>
                            <p className="text-3xl font-bold">R$: {bids && bids.length > 0
                                ? formatCurrencyBR(bids[0].amount)
                                : formatCurrencyBR(auction.startingBid)}</p>
                            <PlaceBidForm auction={auction} />
                        </BorderBox>
                        <div>
                            <div className="flex flex-col gap-2 my-4 text-stone-500">
                                <div>
                                    <div className="flex items-center ">
                                        <User className='mr-2 h-4 w-4' />
                                        Vendedor
                                    </div>
                                    {auction.sellerName}
                                </div>
                                <div>
                                    <div className="flex items-center ">
                                        <Banknote className='mr-2 h-4 w-4' />
                                        Lance inicial
                                    </div>
                                    R$ {formatCurrencyBR(auction.startingBid)}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <BidsHistoryCard bids={bids} />
            </div>
        </div>
    )
}

export default AuctionDetailsPage