//package com.dudesameerkhn.cosc635;

import java.net.DatagramPacket;

public class GBNPacket {
	private DatagramPacket window_packet;
	private boolean isAck;
	private int packet_num;
	private long last_sent;

	public GBNPacket(DatagramPacket p_packet, boolean p_acked, int p_packet_num) {
		this.packet_num = p_packet_num;
		this.window_packet = p_packet;
		this.last_sent = 0;
		this.isAck = p_acked;
	}
	
	public DatagramPacket getPacket() {
		return window_packet;
	}

	public boolean isAcked() {
		return isAck;
	}

	public void packet_ack() {
		this.isAck = true;
	}

	public int getpacket_num() {
		return packet_num;
	}

	public long getTimeLastSent() {
		return last_sent;
	}

	public void setTimeLastSent(long timeLastSent) {
		this.last_sent = timeLastSent;
	}
}
