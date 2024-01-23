import Includes.*;
import java.util.*;
import java.lang.Math;

public class MerkleTree{

	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numdocs;

	public String Build(String[] documents){
		int n=documents.length;
		this.numdocs= n;
		int d=(int)((Math.log(n))/Math.log(2));
        CRF obj= new CRF(64);
		TreeNode leaf[]= new TreeNode[n];

		for (int i=0; i<n; i++){

			leaf[i] = new TreeNode();
			leaf[i].val=documents[i];
			leaf[i].left=null;
			leaf[i].right=null;

			}
		ArrayList<ArrayList<TreeNode>> level = new ArrayList<ArrayList<TreeNode>>();

		int k=1;
		for (int j=0; j<d; j++){
			ArrayList<TreeNode> index= new ArrayList<TreeNode>();
			for (int z=0; z<k; z++){
				TreeNode tn = new TreeNode();
				index.add(tn);
				}
			level.add(index);
			k=k*2;
			}
		k=n/2;
		for(int m=d-1; m>=0; m--){
			for (int g=0; g<k; g++){
				if (m==d-1){

					level.get(m).get(g).val= obj.Fn(leaf[2*g].val+"#"+leaf[2*g+1].val);
					level.get(m).get(g).left=leaf[2*g];
					level.get(m).get(g).right=leaf[2*g+1];

					leaf[2*g].parent=level.get(m).get(g);
					leaf[2*g+1].parent=level.get(m).get(g);
					}
				else{

					level.get(m).get(g).val= obj.Fn(level.get(m+1).get(2*g).val+"#"+level.get(m+1).get(2*g+1).val);
					level.get(m).get(g).left=level.get(m+1).get(2*g);
					level.get(m).get(g).right=level.get(m+1).get(2*g+1);

					level.get(m+1).get(2*g).parent=level.get(m).get(g);
					level.get(m+1).get(2*g+1).parent=level.get(m).get(g);
					}
				}
			k=k/2;
			}

		this.rootnode = level.get(0).get(0);
		this.rootnode.parent=null;
		String summary = this.rootnode.val;
		return summary;

	}

	/*
		Pair is a generic data structure defined in the Pair.java file
		It has two attributes - First and Second, that can be set either manually or
		using the constructor

		Edit: The constructor is added
	*/

	public List<Pair<String,String>> QueryDocument(int doc_idx){
		TreeNode curr = this.rootnode;
		int h = doc_idx;
		int l = this.numdocs;
		int dp = (int)((Math.log(this.numdocs))/Math.log(2));
		List<Pair<String,String>> arr = new ArrayList<Pair<String,String>>();

		for(int x=1; x<=dp; x++){
			if (h<=l/2){
				curr=curr.left;
				l=l/2;
				}
			else {
				curr=curr.right;
				h=h-l/2;
				l=l/2;
				}
			}
		while(curr.parent!=null){

            curr=curr.parent;
            Pair<String, String> p= new Pair<String, String>();
            p.First = curr.left.val;
            p.Second = curr.right.val;
            arr.add(p);
			}
		Pair<String, String> p1= new Pair<String, String>();
		p1.First = curr.val;
		p1.Second = null;
		arr.add(p1);

		return arr;
	}

	public static boolean Authenticate(List<Pair<String,String>> path, String summary){

		CRF o1= new CRF(64);
		boolean bool;
		int l = path.size();
		if (summary.equals(path.get(l-1).First)){
		    bool = true;
			}
		else {
			bool = false;
			}
		for (int w = 1; w<l; w++) {
			Pair<String, String> p1 = path.get(w);
			Pair<String, String> p2 = path.get(w-1);
			String s = o1.Fn(p2.First+"#"+p2.Second);

			if (!(s.equals(p1.First) || s.equals(p1.Second))){
				bool = false;
				}
			}
		return bool;
	}

	public String UpdateDocument(int doc_idx, String new_document){
		CRF o = new CRF(64);
		TreeNode curr = this.rootnode;
		int h = doc_idx;
		int l = this.numdocs;
		int dp = (int)((Math.log(this.numdocs))/Math.log(2));

		for(int x=0; x<dp; x++){
			if (h<=l/2){
				curr=curr.left;
				l=l/2;
				}
			else {
				curr=curr.right;
				h=h-l/2;
				l=l/2;
				}
			}
		curr.val=new_document;
		curr=curr.parent;
		while(curr!=null){
			curr.val=o.Fn(curr.left.val+"#"+curr.right.val);
			if (curr.parent==null) {
				this.rootnode=curr;
				}
			curr=curr.parent;
			}

		String summ = this.rootnode.val;

		return summ;

	}

}