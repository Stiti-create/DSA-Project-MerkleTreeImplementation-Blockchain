package DSCoinPackage;
import HelperClasses.MerkleTree;
import HelperClasses.TreeNode;
import java.util.*;
import java.util.Map;
import java.util.HashMap;
import HelperClasses.Pair;

public class Members
 {

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void initiateCoinsend(String destUID, DSCoin_Honest DSobj) {

	 Transaction tobj = new Transaction();
	 tobj.Source = this;
	 tobj.coinID = this.mycoins.get(0).first;
	 tobj.coinsrc_block = this.mycoins.get(0).second;

	 Members member1 = new Members();
	 for(int i =0; i < DSobj.memberlist.length; i++){
	    member1 = DSobj.memberlist[i];
	    if(member1.UID.equals(destUID)){
	         break;
	       }
	   }
	     tobj.Destination = member1;


	 List<Pair<String, TransactionBlock>> arr = new ArrayList<Pair<String, TransactionBlock>>();
	      for(int i = 1; i < this.mycoins.size(); i++){
	        arr.add(this.mycoins.get(i));
	      }
     this.mycoins = arr;

     if(this.in_process_trans == null){
	    this.in_process_trans = new Transaction[1];
	    this.in_process_trans[0] = tobj;

        }
     else {
		 if(this.in_process_trans[0] == null){

	       this.in_process_trans = new Transaction[1];
           this.in_process_trans[0] = tobj;
	       }

         else{
			 Transaction[] tnew = new Transaction[this.in_process_trans.length + 1];
			 int i = 0;
			 while(i < this.in_process_trans.length){
			        tnew[i] = this.in_process_trans[i];
                    i++;
			       }
			 tnew[i] = tobj;
             this.in_process_trans = tnew;
			   }
	       }

	  DSobj.pendingTransactions.AddTransactions(tobj);
      return ;

  }


  public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {


             List<Pair<String, String>> retlist = new ArrayList<Pair<String, String>>();

		     Pair<List<Pair<String, String>>, List<Pair<String, String>>> ret = new  Pair<List<Pair<String, String>>, List<Pair<String, String>>>(retlist,retlist);


		     TransactionBlock tb1 =  DSObj.bChain.lastBlock;
		     TransactionBlock tb2 = tb1;
		     boolean bool = false;

		     Transaction t = new Transaction();
		     int j =0;
		     while(tb2 != null && !bool){
		       for(int i =0; i < tb2.trarray.length; i++){
		         if(tobj == tb2.trarray[i]){
				   j = i;
				   bool = true;
				   tb1 = tb2;
		           t = tobj;
		           break;
		         }
		       }
		       if(bool)  break;
		       tb2 = tb2.previous;

              }
          if(bool){

		        TreeNode nd = new TreeNode();
		        nd = tb1.Tree.rootnode;
		        int l = tb1.trarray.length;
		        while(nd.left != null){
		          if(j < l/2){
		            nd = nd.left;

		          }
		          else{
		            nd = nd.right;
		            j = j - l/2;

		          }
		          l = l/2;
                  }
          TreeNode secnode = nd;
          while(nd.parent != null){
			  if(nd.parent.left == nd){
			      Pair<String, String> p1 = new Pair<String, String>(nd.val, nd.parent.right.val);
			      ret.first.add(p1);
			     }
			  else{
			      Pair<String, String> p1 = new Pair<String, String>(nd.parent.left.val, nd.val);
			      ret.first.add(p1);
			      }
              nd = nd.parent;

			  }
          Pair<String, String> p1 = new Pair<String, String>(nd.val, null);
	      ret.first.add(p1);


          Pair<String, TransactionBlock> p2 = new Pair<String, TransactionBlock>(tobj.coinID, tb1);


	      TransactionBlock tb3 = tb1;
		  for(int i =0; i < tobj.Destination.mycoins.size(); i++){
		      if(Integer.parseInt(tobj.Destination.mycoins.get(i).first) > Integer.parseInt(tobj.coinID)){
		         tobj.Destination.mycoins.add(i,p2);
		         break;
		         }
              }
          List<Pair<String, String>> temp = new ArrayList<Pair<String, String>>();
		  TransactionBlock tb4 = DSObj.bChain.lastBlock;
	      while(tb4 != tb1.previous){
			      String s = tb4.previous.dgst + "#" + tb4.trsummary + "#" + tb4.nonce;

		          Pair<String, String> p3 = new Pair<String, String>(tb4.dgst, s);

		          temp.add(p3);
		          tb4 = tb4.previous;
		        }
		  if(tb1.previous == null){
		       Pair<String, String> p3 = new Pair<String, String>(null, null);
		       temp.add(p3);
	           }
		  else{
		       Pair<String, String> p3 = new Pair<String, String>(tb4.dgst, null);
		       temp.add(p3);
               }
          List<Pair<String, String>> list2 = new ArrayList<Pair<String, String>>();

		  for(int i = temp.size()-1; i >= 0; i--){
		        list2.add(temp.get(i));
		        }

		  ret.second = list2;

          boolean counter = false;
		  int y =0;
		  int z =0;
          while(!counter){

		      if(this.in_process_trans[y] == tobj){
		          counter = true;
		          z = y;
		          }
		      y++;
           }
           y = 0;
		   int x = 0;
           Transaction[] sample = new Transaction[this.in_process_trans.length-1];
           while(y < in_process_trans.length-1){
		       if(x == z)  x++;
               else {
				   sample[y] = this.in_process_trans[x];
				   y++;
				   x++;
				   }
		       }

		   this.in_process_trans = sample;

   }
   else {
	   throw new MissingTransactionException();
	   }

		return ret;
  }

  public void MineCoin(DSCoin_Honest DSObj) {
	 try{
		  int c = DSObj.bChain.tr_count;
		  	  HashMap<String, Transaction> valid = new HashMap<>();

		  	  TransactionQueue tq = new TransactionQueue();
		  	  tq = DSObj.pendingTransactions;
		  	  Transaction t = new Transaction();
		  	  t = tq.firstTransaction;
		  	  TransactionBlock tb =  t.coinsrc_block;


		  	  Transaction minerRewardTransaction = new Transaction();
		  	  minerRewardTransaction.coinID = Integer.toString(Integer.parseInt(DSObj.latestCoinID)+1);
		  	  minerRewardTransaction.Source = null;
		  	  minerRewardTransaction.Destination = this;
		  	  minerRewardTransaction.coinsrc_block = null;


		        Transaction[] arr = new Transaction[c];

		  	  int ct = 0;
		  	  if (tb.checkTransaction(t)){
		  		  valid.put(t.coinID, t);
		  		  tq.RemoveTransaction();
		  		  arr[ct] = t;
		  		  ct++;
		  		  }

	  	  while(t.next.next!=null && ct<c){
		  		  if (tb.checkTransaction(t.next)) {
		  			  if (valid.containsKey(t.next.coinID)){
		                    t.next = t.next.next;

		  				  }
		  			  else {
		  				  valid.put(t.next.coinID, t.next);
		  				  t.next = t.next.next;
		  				  arr[ct] = t;
		  				  ct++;
		  				  }
		  			  }
		  		  t = t.next;
		  		  }
		  		if (t.next.next==null && ct<c-1){
					if (tb.checkTransaction(t.next)) {
					    if (valid.containsKey(t.next.coinID)){
					       t.next = null;

					    }
				        else {
					        valid.put(t.next.coinID, t.next);
				            t.next = null;
				   		    arr[ct] = t;

							  }
				        }
		  		 }
                arr[c-1] = minerRewardTransaction;
		        TransactionBlock tB = new TransactionBlock(arr);
		        tB.tr_count = c;
		        MerkleTree mtree = new MerkleTree();
		        tB.trsummary = mtree.Build(tB.trarray);
		        tB.Tree = mtree;
		        DSObj.bChain.InsertBlock_Honest(tB);

		        Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(minerRewardTransaction.coinID,  tB);

		        this.mycoins.add(p);

      DSObj.latestCoinID = minerRewardTransaction.coinID ;
		  }catch (EmptyQueueException e){
			  System.out.println("Empty Queue");
			  }

	   return;

}


  public void MineCoin(DSCoin_Malicious DSObj) {
      try{
		    int c = DSObj.bChain.tr_count;
		           TransactionQueue tq = new TransactionQueue();
		  	  	  tq = DSObj.pendingTransactions;
		          Transaction t = new Transaction();
		  	  	  t = tq.firstTransaction;
		  	  	  HashMap<String, Transaction> valid = new HashMap<>();
		  	  	  TransactionBlock tb =  t.coinsrc_block;



		  	  	  Transaction minerRewardTransaction = new Transaction();
		  	  	  minerRewardTransaction.coinID = Integer.toString(Integer.parseInt(DSObj.latestCoinID)+1);
		  	  	  minerRewardTransaction.Source = null;
		  	  	  minerRewardTransaction.Destination = this;
		  	  	  minerRewardTransaction.coinsrc_block = null;


		            Transaction[] arr = new Transaction[c];
		  	  	  int ct = 0;
		  	  	  if (tb.checkTransaction(t)){

		  	  		  valid.put(t.coinID, t);
		  	  		  tq.RemoveTransaction();
		  	  		  arr[ct] = t;
		  	  		  ct++;
		  	  		  }

		  	  	  while(t.next.next!=null && ct<c){
		  	  		  if (tb.checkTransaction(t.next)) {
		  	  			  if (valid.containsKey(t.next.coinID)){
		  	                    t.next = t.next.next;

		  	  				  }
		  	  			  else {
		  	  				  valid.put(t.next.coinID, t.next);
		  	  				  t.next = t.next.next;
		  	  				  arr[ct] = t;
		  	  				  ct++;
		  	  				  }
		  	  			  }
		  	  		  t = t.next;
		  	  		  }
		        arr[c-1] = minerRewardTransaction;
		        TransactionBlock tB = new TransactionBlock(arr);
		        tB.tr_count = c;
		        MerkleTree mtree = new MerkleTree();
		  	  tB.trsummary = mtree.Build(tB.trarray);
		        tB.Tree = mtree;
		        DSObj.bChain.InsertBlock_Malicious(tB);

		        Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(minerRewardTransaction.coinID,  tB);

		        this.mycoins.add(p);


		        DSObj.latestCoinID = minerRewardTransaction.coinID ;

		  }catch (EmptyQueueException e){
			  System.out.println("Empty Queue");
			  }
  }
}
