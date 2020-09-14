package projects.filenamecomments;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ProjectViewNodeDecorator;
import com.intellij.ide.projectView.impl.nodes.ClassTreeNode;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packageDependencies.ui.PackageDependenciesNode;
import com.intellij.ui.ColoredTreeCellRenderer;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class FileNameDecorator implements ProjectViewNodeDecorator {

  private static final Pattern COMMENT_PATTERN = Pattern.compile("/\\*\\*\\n \\*.+\\n \\*/");

  @Override
  public void decorate(ProjectViewNode node, PresentationData data) {
    if (node instanceof ClassTreeNode) {
      VirtualFile virtualFile = node.getVirtualFile();
      if (virtualFile == null || !StringUtils.equals(virtualFile.getExtension(), "java")) {
        return;
      }
      String content;
      try {
        content = VfsUtil.loadText(virtualFile);
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
      Matcher matcher = COMMENT_PATTERN.matcher(content);
      if (matcher.find()) {
        String commentStr = matcher.group(0);
        String s = commentStr.split("\n")[1];
        String commentBrief = s.substring(s.indexOf("*") + 1);
        String presentableText = virtualFile.getNameWithoutExtension() + " " + commentBrief;
        data.setPresentableText(presentableText);
      }
    }
  }

  @Override
  public void decorate(PackageDependenciesNode node, ColoredTreeCellRenderer cellRenderer) {

  }
}
