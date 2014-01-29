package phaseA;
import javax.print.attribute.standard.Sides;

import providedCode.*;


/**
 * And AVLTree is a type of Binary Search Tree that assures good balance. It can be used in
 * the same way as a BST -- but it guarantees O(log(n)) time for common operations. 
 */
public class AVLTree<E> extends BinarySearchTree<E> {

	public AVLTree(Comparator<? super E> c) {
		super(c);
	}

	/** {@inheritDoc} */
	public void incCount(E data) {
		// if first node, we can just add it
		if (overallRoot == null) {
			overallRoot = new AVLNode(data);
			return;
		}

		// Place the node
		AVLNode currentNode = (AVLNode) overallRoot;
		while (true) {
			// compare the new data with the data at the current node
			int cmp = comparator.compare(data, currentNode.data);
			if(cmp == 0) {            // a. Current node is a match
				currentNode.count++;
				return;
			} else if (cmp < 0) {       // b. Data goes left of current node
				if (currentNode.left == null) {
					currentNode.left = new AVLNode(data, currentNode);
					break;
				}	
				currentNode = (AVLNode) currentNode.left;
			} else {                    // c. Data goes right of current node
				if(currentNode.right == null) {
					currentNode.right = new AVLNode(data, currentNode);
					break;
				}
				currentNode = (AVLNode) currentNode.right;
			}
		}

		// set the height of all nodes above our placed node in the tree
		percHeightUp(currentNode);

		// at this stage, we have put the new node in the proper place
		// and fixed heights for everything. next we need to rotate

		while (currentNode != null) {
			Side isImbalanced = checkImbalance(currentNode);
			if (isImbalanced != Side.BALANCED) { 
				// rotate and fix
				percHeightDown( rotate(currentNode, isImbalanced) );
				break; 
			}
			currentNode = currentNode.parent;
		}

	}

	/** private helper methods :: ROTATIONS============= **/
	
	/** Balances an AVL(sub)tree
	 * @param current The head node fo the subtree to balance
	 * @param imbalance The side that is too tall
	 * @return The top node of the new tree
	 */
	private AVLNode rotate(AVLNode current, Side imbalance) {
		if (imbalance == Side.LEFT) {
			current = (AVLNode) current.left;
			int left_height = (current.left == null) ? -1 : ((AVLNode) current.left).height;
			int right_height = (current.right == null) ? -1 : ((AVLNode) current.right).height;
			if (left_height > right_height) { return rotateLeftLeft(current.parent); }
			else { return rotateLeftRight(current.parent); }
		} else {
			current = (AVLNode) current.right;
			int left_height = (current.left == null) ? -1 : ((AVLNode) current.left).height;
			int right_height = (current.right == null) ? -1 : ((AVLNode) current.right).height;
			if (left_height > right_height) { return rotateRightLeft(current.parent); }
			else { return rotateRightRight(current.parent); }
		}
	}

	/** Fix a subtree in a certain case
	 * @param parent The top of the subtree to fix
	 * @return The top of the resulting subtree
	 */
	private AVLNode rotateLeftRight(AVLNode parent) {

		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.left;
		AVLNode moveToTop = (AVLNode) current.right;
		AVLNode LRL = (AVLNode) moveToTop.left;
		AVLNode LRR = (AVLNode) moveToTop.right;

		// set the top
		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = moveToTop;;
			moveToTop.parent = null;
		} else if (parent.parent.left == parent) {
			// we are the left child
			parent.parent.left = moveToTop;
			moveToTop.parent = parent.parent;
		} else {
			parent.parent.right = moveToTop;
			moveToTop.parent = parent.parent;
		}

		current.right = LRL;
		if (LRL != null) { LRL.parent = current; }

		parent.left = LRR;
		if (LRR != null) { LRR.parent = parent; }

		moveToTop.left = current;
		moveToTop.right = parent;
		current.parent = moveToTop;
		parent.parent = moveToTop;
		
		return moveToTop;
	}

	/** Fix a subtree in a certain case
	 * @param parent The top of the subtree to fix
	 * @return The top of the resulting subtree
	 */
	private AVLNode rotateRightLeft(AVLNode parent) {

		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.right;
		AVLNode moveToTop = (AVLNode) current.left;
		AVLNode RLR = (AVLNode) moveToTop.right;
		AVLNode RLL = (AVLNode) moveToTop.left;

		// set the top
		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = moveToTop;;
			moveToTop.parent = null;
		} else if (parent.parent.right == parent) {
			// we are the right child
			parent.parent.right = moveToTop;
			moveToTop.parent = parent.parent;
		} else {
			parent.parent.left = moveToTop;
			moveToTop.parent = parent.parent;
		}

		current.left = RLR;
		if (RLR != null) { RLR.parent = current; }

		parent.right = RLL;
		if (RLL != null) { RLL.parent = parent; }

		moveToTop.right = current;
		moveToTop.left = parent;
		current.parent = moveToTop;
		parent.parent = moveToTop;
		
		return moveToTop;

	}

	/** Fix a subtree in a certain case
	 * @param parent The top of the subtree to fix
	 * @return The top of the resulting subtree
	 */
	private AVLNode rotateLeftLeft(AVLNode parent) {
		// the left subtree is too tall -- and the left subtree of that is the problem
		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.left;

		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = current;
			current.parent = null;
		} else if (parent.parent.left == parent) {
			// we are the left child
			parent.parent.left = current;
			current.parent = parent.parent;
		} else {
			parent.parent.right = current;
			current.parent = parent.parent;
		}

		// now, we don't have to worry about how we are connected above
		parent.left = current.right;
		if (current.right != null) { ((AVLNode) current.right).parent = parent; }

		// now the right child of current is transnferred to parent
		current.right = parent;
		parent.parent = current;
		
		return current;
	}

	/** Fix a subtree in a certain case
	 * @param parent The top of the subtree to fix
	 * @return The top of the resulting subtree
	 */
	private AVLNode rotateRightRight(AVLNode parent) {
		// the right subtree is too tall -- and the right subtree of that is the problem
		AVLNode parentNode = parent;
		AVLNode current = (AVLNode) parentNode.right;

		if (parent.parent == null) {
			// it is overallRoot
			this.overallRoot = current;
			current.parent = null;
		} else if (parent.parent.right == parent) {
			// we are the right child
			parent.parent.right = current;
			current.parent = parent.parent;
		} else {
			parent.parent.left = current;
			current.parent = parent.parent;
		}

		// now, we don't have to worry about how we are connected above
		parent.right = current.left;
		if (current.left != null) { ((AVLNode) current.left).parent = parent; }

		// now the left child of current is transnferred to parent
		current.left = parent;
		parent.parent = current;

		return current;
	}

	/** private helper methods: setting heights **/
	/** Fixes the height of all nodes above a given node
	 * @param current the node which we fix the height of all nodes above
	 */
	private void percHeightUp(AVLNode current) {
		while (current != null) {
			setHeight(current);
			current = current.parent;
		}
	}

	/** Fixes the height of all nodes below a certain node
	 * @param current The node to fix (and fix height of nodes below)
	 * @return The height of the given node
	 */
	private int percHeightDown(AVLNode current) {
		if (current == null) {
			return -1;
		}
		System.out.println(current.left + " " + current.right);
		int l = percHeightDown((AVLNode) current.left); 
		int r = percHeightDown((AVLNode) current.right); 

		current.height = 1 + Math.max(l,  r);
		return current.height;
	}

	/** Sets of the height of a given node based on the height of its subnodes
	 * @param current The node to set the height of 
	 */
	private void setHeight(AVLNode current) {
		int left_height = (current.left == null) ? -1 : ((AVLNode) current.left).height;
		int right_height = (current.right == null) ? -1 : ((AVLNode) current.right).height;
		current.height = 1 + Math.max(left_height, right_height);
	}

	private Side checkImbalance(AVLNode current) {
		// imbalanced if one is noticable taller than other
		int left_height = (current.left == null) ? -1 : ((AVLNode) current.left).height;
		int right_height = (current.right == null) ? -1 : ((AVLNode) current.right).height;
		if (left_height - right_height > 1) { return Side.LEFT; }
		else if (right_height - left_height > 1) { return Side.RIGHT; }
		else { return Side.BALANCED; }
	}
	
	/** Private helper methods: error checking =============================**/

	/** Check if this is a valid tree
	 * @return True iff. this is a valid AVLTree
	 */
	public boolean isValidAVL(){
		try {
			checkAVL((AVLNode) this.overallRoot);
		} catch (IllegalStateException e) {
			return false;
		}
		return true;
	}

	/** Check if a single subtree is a valid AVL tree - balanced and with correct heights
	 * @param c the subtree to check
	 * @return the height of the subtree
	 */
	private int checkAVL(AVLNode c) {
		if (c == null) {
			return -1;
		}
		int left = checkAVL((AVLNode) c.left);
		int right = checkAVL((AVLNode) c.right);
		int left_height = (c.left == null) ? -1 : ((AVLNode) c.left).height;
		int right_height = (c.right == null) ? -1 : ((AVLNode) c.right).height;

		if (left != left_height || right != right_height) {
			throw new IllegalStateException("Height not correct: " + left + " " + left_height + " " + right + " " + right_height);
		} else if (Math.abs(left - right) > 1) {
			throw new IllegalStateException("Not balanced: " + left + " " + right);
		}
		return 1 + Math.max(left, right);
	}

	/** Private classes ==================== **/
	protected class AVLNode extends BSTNode {
		public AVLNode parent;
		public int height;

		public AVLNode(E data, AVLNode parent) {
			super(data);
			this.parent = parent;
			this.height = 0;
		}

		public AVLNode(E data) {
			this(data, null);
		}
	}

	private enum Side {
		LEFT, RIGHT, BALANCED;
	}
}


