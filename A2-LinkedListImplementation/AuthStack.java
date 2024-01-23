import Includes.*;

public class AuthStack{
	// PLEASE USE YOUR ENTRY NUMBER AS THE START STRING
	private static final String start_string = "2020CS10394";
	private StackNode top;

	/*
		Note that the Exceptions have already been defined for you in the included file,
		you just have to raise them accordingly

	*/

	/*
	Notice that this function is static, the reason why this is static is that we don't want this to be tied with
	an object of the class.
	*/
	public static boolean CheckStack(AuthStack current, String proof) throws AuthenticationFailedException{

		CRF obj = new CRF(64);
				StackNode curr = current.top;
				boolean bool = true;
				while(curr != null){
					if(curr.below==null){
						String hsh = obj.Fn(AuthStack.start_string + "#" + curr.data.value);
						if(!curr.dgst.equals(hsh)) {
							throw new AuthenticationFailedException();
						}

					}else if(bool){
						String hsh = obj.Fn(curr.below.dgst + "#" + curr.data.value);
						if(!curr.dgst.equals(proof)) {
							throw new AuthenticationFailedException();
						}
						bool=false;

					}else{
						String hsh = obj.Fn(curr.below.dgst + "#" + curr.data.value);
						if(!curr.dgst.equals(hsh))  {
							throw new AuthenticationFailedException();
						}

					}
					curr=curr.below;
				}
		return true;
	}

	public String push(Data datainsert, String proof)throws AuthenticationFailedException{
        CRF ob=new CRF(64);
        try{
		    if (CheckStack(this,proof)){
				StackNode newStack= new StackNode();
				newStack.data=datainsert;
				if (this.top==null){
				    newStack.below=null;
				    this.top=newStack;
				    newStack.dgst=ob.Fn(this.start_string+"#"+newStack.data.value);
				}
				else{
				    newStack.below=this.top;
				    this.top=newStack;
				    newStack.dgst=ob.Fn(newStack.below.dgst+"#"+newStack.data.value);
				}
			proof=this.top.dgst;
			}
		}catch (AuthenticationFailedException er1){
			System.out.println("Auth error");
		}finally{
		    return proof;
		}


	}

	public String pop(String proof)throws AuthenticationFailedException, EmptyStackException{

      try {
		  if (CheckStack(this, proof)){
			  if (this.top==null){
				  throw new EmptyStackException();
				  }
			  else {
				  if (this.top.below==null){
					  this.top=null;
					  proof=null;
					  }
				  else {
					  this.top=this.top.below;
					  proof=this.top.dgst;
					  }
				  }
			  }
		  return proof;
		  }
	  catch (AuthenticationFailedException ea){
		  return null;
		  }
	  catch (EmptyStackException ee) {
		  return null;
		  }

	}

	public StackNode GetTop(String proof)throws AuthenticationFailedException{
        StackNode retNode=new StackNode();
		try {
		    if (CheckStack(this, proof)){
                if (this.top!=null){
					retNode=this.top;
					}
				else{
					retNode=null;
					}

			}
		}catch (AuthenticationFailedException er2){
			System.out.println("Auth error");


	    }finally{
			return retNode;
	}
	}

}