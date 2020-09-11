package projects.filenamecomments;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileNameTreeStructureProvider implements TreeStructureProvider {

  @Override
  public @NotNull Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent,
      @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
    ArrayList<AbstractTreeNode> nodes = new ArrayList<>();
    for (AbstractTreeNode child : children) {
      if (child instanceof PsiFileNode) {
        PsiFileNode psiFileNode = (PsiFileNode) child;

      }
      nodes.add(child);
    }
    return nodes;
  }

  @Override
  public @Nullable Object getData(@NotNull Collection<AbstractTreeNode> selected,
      @NotNull String dataId) {
    return null;
  }


}
