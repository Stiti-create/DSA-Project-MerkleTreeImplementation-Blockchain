package DSCoinPackage;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;

public void AddTransactions (Transaction transaction) {
	  if (this.numTransactions==0){
		  this.firstTransaction = transaction;
		  this.lastTransaction = transaction;

		  }
      else {
		  this.lastTransaction.next = transaction;
		  this.lastTransaction = transaction;
		  }
	  transaction.next = null;
	  this.numTransactions ++;
  }

  public Transaction RemoveTransaction () throws EmptyQueueException {

   try {

	    Transaction f = null;
	    if (this.numTransactions==0) throw new EmptyQueueException();
	       else{
	   		f = this.firstTransaction;
	   		if (this.numTransactions==1){
	   			this.firstTransaction = null;
	   			this.lastTransaction = null;
	   			}
	   		else{
	   			this.firstTransaction = this.firstTransaction.next;

	   			}
	   		this.numTransactions--;
	   		}
            return f;
	   } catch (EmptyQueueException e){
		   System.out.println("Empty Queue Exception");
		   }

	return null;
  }

  public int size() {
	int s = this.numTransactions ;
    return s;
  }
}

