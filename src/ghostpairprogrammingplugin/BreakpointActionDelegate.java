package ghostpairprogrammingplugin;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaExceptionBreakpoint;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;

import as_is_prog.ukagaka.UkagakaSSTPConnection;

public class BreakpointActionDelegate implements IJavaBreakpointListener {

	@Override
	public void breakpointHasRuntimeException(IJavaLineBreakpoint breakpoint, DebugException exception) {
		System.err.println("rntException");
	}

	@Override
	public void addingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// TODO Auto-generated method stub
		System.err.println("add:"+arg1.getClass().getName());
	}

	@Override
	public void breakpointHasCompilationErrors(IJavaLineBreakpoint arg0, Message[] arg1) {
		// TODO Auto-generated method stub
		System.err.println("errors");
	}

	@Override
	public int breakpointHit(IJavaThread arg0, IJavaBreakpoint arg1) {

		try {
			System.err.println("hit:"+arg1.getTypeName());
			IJavaExceptionBreakpoint jebp = (IJavaExceptionBreakpoint)arg1;
			File file = (File)arg0.getLaunch().getSourceLocator().getSourceElement(arg0.getTopStackFrame());
			System.err.println(file.getName());
			System.err.println(jebp.getExceptionTypeName());
			System.err.println(jebp.getClass().getName());
			System.err.println(arg0.getTopStackFrame().getLineNumber());



			try {
				new UkagakaSSTPConnection("test").sendNotify1_1("OnGIPSExceptionOccured", "java",jebp.getClass().getName(),file.getName(),String.valueOf(arg0.getTopStackFrame().getLineNumber()),"");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public void breakpointInstalled(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointRemoved(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public int installingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1, IJavaType arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
