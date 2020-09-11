package projects.filenamecomments;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.ui.SimpleTextAttributes;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class PsiCommentsFileNode extends PsiFileNode {

  public PsiCommentsFileNode(Project project,
      @NotNull PsiFile value,
      ViewSettings viewSettings) {
    super(project, value, viewSettings);
  }

  @Override
  protected @NotNull PresentationData createPresentation() {

    PresentationData presentation = super.createPresentation();
    try {
      VirtualFile virtualFile = this.getVirtualFile();
      if (virtualFile == null) {
        return presentation;
      }
      String content = VfsUtil.loadText(virtualFile);
      int publicClassIndex = content.indexOf("public class");
      if (publicClassIndex == 0) {
        return presentation;
      }
      int firstCommentIndex = content.indexOf("/**");
      if (firstCommentIndex == 0 || firstCommentIndex >= publicClassIndex) {
        return presentation;
      }
      int commentEndIndex = content.indexOf('\n', firstCommentIndex);
      presentation.addText(this.myName, SimpleTextAttributes.GRAY_ATTRIBUTES);
      presentation.addText(content.substring(firstCommentIndex, commentEndIndex),
          SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES);
    } catch (IOException e) {
      System.out.println("error");
    }

    return presentation;
  }
}
