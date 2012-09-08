package hu.blummers.bitcoin.core;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import hu.blummers.bitcoin.core.WireFormat.Reader;
import hu.blummers.bitcoin.core.WireFormat.Writer;

public class AddrMessage extends BitcoinMessage {

	private List<WireFormat.Address> addresses = new ArrayList<WireFormat.Address> ();
	
	public AddrMessage(Chain chain, String command) {
		super(chain, command);
	}

	@Override
	public void toWire(Writer writer) {
		writer.writeVarInt(addresses.size());
		for ( WireFormat.Address a : addresses )
		{
			writer.writeUint64(new BigInteger ("1"));
			writer.writeAddress(a);
		}
	}

	@Override
	public void fromWire(Reader reader) {
		long n = reader.readVarInt();
		for ( long i = 0; i < n; ++i )
		{
			addresses.add(reader.readAddress());
		}
	}

	public List<WireFormat.Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<WireFormat.Address> addresses) {
		this.addresses = addresses;
	}

}