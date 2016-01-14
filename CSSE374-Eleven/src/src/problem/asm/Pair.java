package src.problem.asm;

public class Pair<C, M> {

	private C left;
	private M right;

	public C getLeft() {
		return left;
	}

	public void setLeft(C left) {
		this.left = left;
	}

	public M getRight() {
		return right;
	}

	public void setRight(M right) {
		this.right = right;
	}

	public Pair(C left, M right) {
		this.left = left;
		this.right = right;
	}

}
