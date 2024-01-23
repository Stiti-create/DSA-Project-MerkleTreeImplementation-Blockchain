import Includes.*;

public class AuthList{
	// PLEASE USE YOUR ENTRY NUMBER AS THE START STRING
	public static final String start_string = "2018CS50402";
	public Node firstNode;
	public Node lastNode;

	/*
		Note that the Exceptions have already been defined for you in the includes file,
		you just have to raise them accordingly

	*/

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class AuthList. 	
	*/
	public static boolean CheckList(AuthList current, String proof) throws AuthenticationFailedException {
		CRF obj = new CRF(64);
		Node curr = current.firstNode;
		boolean initial = true;
		while(curr != null){
			if(initial){
				String hsh = obj.Fn(AuthList.start_string + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh)) {
					throw new AuthenticationFailedException();
				}
				initial = false;
				curr = curr.next;
			}else if(curr == current.lastNode){
				String hsh = obj.Fn(curr.previous.dgst + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh) || !curr.dgst.equals(proof)) {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}else{
				String hsh = obj.Fn(curr.previous.dgst + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh))  {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}
		}
		return true;
	}


	public String InsertNode(Data datainsert, String proof) throws AuthenticationFailedException {
		/*
			Implement Code here
		*/

		return null;
	}

	public String DeleteFirst(String proof) throws AuthenticationFailedException, EmptyListException {
		/*
			Implement Code here
		*/

		return null;
	}


	public String DeleteLast(String proof) throws AuthenticationFailedException, EmptyListException {
		/*
			Implement Code here
		*/

		return null;
	}

	/* 
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class AuthList. 	
	*/
	public static Node RetrieveNode(AuthList current, String proof, Data data) throws AuthenticationFailedException, DocumentNotFoundException{
		/*
			Implement Code here
		*/
		return null;
	}

	public void AttackList(AuthList current, String new_data)throws EmptyListException{
		/*
			Implement Code here
		*/
	}

}
