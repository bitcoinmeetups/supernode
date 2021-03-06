/*
 * Copyright 2012 Tamas Blummer tamas@bitsofproof.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bitsofproof.supernode.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.ByteString;

public class AccountStatement implements Serializable
{
	private static final long serialVersionUID = 4980228520214476462L;

	private String lastBlock;
	private long timestamp;
	private List<TransactionOutput> opening;
	private List<Posting> posting;
	private List<Transaction> unconfirmedSpend;
	private List<Transaction> unconfirmedReceive;

	public String getLastBlock ()
	{
		return lastBlock;
	}

	public void setLastBlock (String lastBlock)
	{
		this.lastBlock = lastBlock;
	}

	public long getTimestamp ()
	{
		return timestamp;
	}

	public void setTimestamp (long timestamp)
	{
		this.timestamp = timestamp;
	}

	public List<TransactionOutput> getOpening ()
	{
		return opening;
	}

	public void setOpening (List<TransactionOutput> opening)
	{
		this.opening = opening;
	}

	public List<Posting> getPosting ()
	{
		return posting;
	}

	public void setPosting (List<Posting> posting)
	{
		this.posting = posting;
	}

	public List<Transaction> getUnconfirmedSpend ()
	{
		return unconfirmedSpend;
	}

	public void setUnconfirmedSpend (List<Transaction> unconfirmedSpend)
	{
		this.unconfirmedSpend = unconfirmedSpend;
	}

	public List<Transaction> getUnconfirmedReceive ()
	{
		return unconfirmedReceive;
	}

	public void setUnconfirmedReceive (List<Transaction> unconfirmedReceive)
	{
		this.unconfirmedReceive = unconfirmedReceive;
	}

	public BCSAPIMessage.AccountStatement toProtobuf ()
	{
		BCSAPIMessage.AccountStatement.Builder builder = BCSAPIMessage.AccountStatement.newBuilder ();

		builder.setLastBlock (ByteString.copyFrom (new Hash (lastBlock).toByteArray ()));
		builder.setBcsapiversion (1);
		builder.setTimestamp ((int) timestamp);
		if ( opening != null )
		{
			for ( TransactionOutput o : opening )
			{
				builder.addOpening (o.toProtobuf ());
			}
		}
		if ( posting != null )
		{
			for ( Posting p : posting )
			{
				builder.addPosting (p.toProtobuf ());
			}
		}
		if ( unconfirmedSpend != null )
		{
			for ( Transaction t : unconfirmedSpend )
			{
				builder.addUnconfirmedSpend (t.toProtobuf ());
			}
		}
		if ( unconfirmedReceive != null )
		{
			for ( Transaction t : unconfirmedReceive )
			{
				builder.addUnconfirmedReceive (t.toProtobuf ());
			}
		}

		return builder.build ();
	}

	public static AccountStatement fromProtobuf (BCSAPIMessage.AccountStatement pa)
	{
		AccountStatement a = new AccountStatement ();
		a.setLastBlock (new Hash (pa.getLastBlock ().toByteArray ()).toString ());
		a.setTimestamp (pa.getTimestamp ());
		if ( pa.getOpeningCount () > 0 )
		{
			a.setOpening (new ArrayList<TransactionOutput> ());
			for ( BCSAPIMessage.TransactionOutput o : pa.getOpeningList () )
			{
				a.getOpening ().add (TransactionOutput.fromProtobuf (o));
			}
		}
		if ( pa.getPostingCount () > 0 )
		{
			a.setPosting (new ArrayList<Posting> ());
			for ( BCSAPIMessage.AccountStatement.Posting o : pa.getPostingList () )
			{
				a.getPosting ().add (Posting.fromProtobuf (o));
			}
		}
		if ( pa.getUnconfirmedSpendCount () > 0 )
		{
			a.setUnconfirmedSpend (new ArrayList<Transaction> ());
			for ( BCSAPIMessage.Transaction o : pa.getUnconfirmedSpendList () )
			{
				a.getUnconfirmedSpend ().add (Transaction.fromProtobuf (o));
			}
		}
		if ( pa.getUnconfirmedReceiveCount () > 0 )
		{
			a.setUnconfirmedReceive (new ArrayList<Transaction> ());
			for ( BCSAPIMessage.Transaction o : pa.getUnconfirmedReceiveList () )
			{
				a.getUnconfirmedReceive ().add (Transaction.fromProtobuf (o));
			}
		}
		return a;
	}
}
