import { useParams } from "react-router";
import { getAuctionsById } from "../../../service/auctionService";
import { useEffect, useState } from "react";
import Navbar from "../../layout/Navbar";
import { Client } from '@stomp/stompjs';
import { getBidsByAuctionId } from '../../../service/bidService';
import BidPanel from './BidPanel';
import BidHistory from './BidHistory';
import ProductDetail from "./ProductDetail";

function AuctionDetailPage() {

    const { id } = useParams();
    const [auction, setAuction] = useState(null);
    const [bids, setBids] = useState([]);
    const [highestBid, setHighestBid] = useState();

    useEffect(() => {
        fetchAuctionById();
        fetchBidsByAuctionId();
        connectWebSocket();
    }, [id]);

    const fetchAuctionById = async () => {
        try {
            const response = await getAuctionsById(id);
            const auctionData = response.data;
            setAuction(auctionData);
        } catch (error) {
            console.error("Erro ao buscar leilões:", error);
        }
    };

    const fetchBidsByAuctionId = async () => {
        try {
            const response = await getBidsByAuctionId(id);
            const bidsData = response.data;
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
                    setHighestBid(bids[0].amount)
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
            <div className='max-w-6xl m-auto'>
                <div className='flex flex-col md:flex-row gap-4 p-4'>
                    <div className='flex-2 flex flex-col gap-4'>
                        <div className="p-4 border border-base-200 shadow-md rounded-lg">
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <img
                                    className='w-full object-cover rounded-lg'
                                    src="/placeholder.svg"
                                    alt=""
                                />
                                <div className="grid grid-cols-2 gap-4">
                                    <img
                                        src="/placeholder.svg"
                                        alt=""
                                        className="w-full object-cover rounded-lg"
                                    />
                                    <img
                                        src="/placeholder.svg"
                                        alt=""
                                        className="w-full object-cover rounded-lg"
                                    />
                                    <img
                                        src="/placeholder.svg"
                                        alt=""
                                        className="w-full object-cover rounded-lg"
                                    />
                                    <img
                                        src="/placeholder.svg"
                                        alt=""
                                        className="w-full object-cover rounded-lg"
                                    />
                                </div>
                            </div>
                        </div>
                        <ProductDetail auction={auction} />
                    </div>
                    <div className='flex-1 flex flex-col gap-4'>
                        <BidPanel auction={auction} highestBid={highestBid} />
                        <BidHistory bids={bids} />
                    </div>
                </div>

            </div>
        </div>
    )
}

export default AuctionDetailPage