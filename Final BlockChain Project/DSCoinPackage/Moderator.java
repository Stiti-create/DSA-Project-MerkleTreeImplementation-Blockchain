package DSCoinPackage;
import java.util.HashMap;
import HelperClasses.Pair;
import HelperClasses.MerkleTree;
import java.util.*;
public class Moderator
 {

  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {
	  int m = DSObj.memberlist.length;
	  int k = coinCount / m;
	  int str = 100000;
	  int l = DSObj.bChain.tr_count;
	  int n = coinCount/l;
	  TransactionBlock[] tbarr = new TransactionBlock[n];

	  Members moderator = new Members();
      moderator.UID = "Moderator";
      Transaction[] store = new Transaction[coinCount];
      for(int i=0; i<coinCount; i++){
		  Transaction tr = new Transaction();
		  tr.Source = moderator;
		  tr.coinID = Integer.toString(str+i);
		  tr.Destination = DSObj.memberlist[i%m];
          store[i]=tr;
		 // DSObj.pendingTransactions.AddTransactions(tr);
		  }

	  for (int f=0; f<n; f++){
	  	  Transaction[] tray = new Transaction[l];
	  	  for (int g=0; g<l; g++){
	  		  tray[g]=store[f*l+g];
	  		  }

	      tbarr[f] = new TransactionBlock(tray);
	      DSObj.bChain.InsertBlock_Honest(tbarr[f]);
	      }
      for (int i=0; i<coinCount; i++){

	      Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(store[i].coinID, tbarr[i/l]);

	  	  store[i].Destination.mycoins.add(p);

	  	  }


	  DSObj.bChain.lastBlock = tbarr[n-1];

	  DSObj.latestCoinID = Integer.toString(str+coinCount);


  }

  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {
	   int m = DSObj.memberlist.length;
	  	  int k = coinCount / m;
	  	  int str = 100000;
	  	  int l = DSObj.bChain.tr_count;
	  	  int n = coinCount/l;
	  	  Members moderator = new Members();
	        moderator.UID = "Moderator";

	        TransactionBlock[] tbarr = new TransactionBlock[n];

				  HashMap<String, Transaction> transact = new HashMap<>();
				   HashMap<String, TransactionBlock> hm = new HashMap<>();
				  for (int q=0; q<coinCount ; q++){
					  Transaction t = new Transaction();
					  t.coinID = Integer.toString(str+q);
					  t.Source = moderator;
					  t.coinsrc_block = null;
					  t.Destination = null;
					  transact.put(t.coinID, t);
					  }
			      for (int f=0; f<n; f++){
					  Transaction[] tray = new Transaction[l];
					  for (int g=0; g<l; g++){
						  tray[g]=transact.get(str+f*l+g);
						  }
					  tbarr[f]=new TransactionBlock(tray);
					  tbarr[f].tr_count = l;
					  }
				  for (int f=0; f<n; f++){
				  		  for (int g=0; g<l; g++){
				  			  hm.put(Integer.toString(str+f*l+g), tbarr[f]);
				  			  }
					  }


				  for (int i=0; i<m; i++){

					  for (int j=0; j<k; j++){
						 // transact.get(Integer.toString(str+i+m*j)).Destination = DSObj.memberlist[i];
			              hm.get(Integer.toString(str+i+m*j)).trarray[(i+m*j)%l].Destination = DSObj.memberlist[i];
			              Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(Integer.toString(str+i+m*j), hm.get(Integer.toString(str+i+m*j)));
						  DSObj.memberlist[i].mycoins.add(p);
						  }

					  }

			      for (int w=0; w<n; w++){
					  MerkleTree mt = new MerkleTree();
					  tbarr[w].trsummary = mt.Build(tbarr[w].trarray);
					  tbarr[w].Tree = mt;
					  }


	  	  DSObj.bChain.lastBlocksList[0] = tbarr[n-1];
	  DSObj.latestCoinID = Integer.toString(str+coinCount);

  }
}
