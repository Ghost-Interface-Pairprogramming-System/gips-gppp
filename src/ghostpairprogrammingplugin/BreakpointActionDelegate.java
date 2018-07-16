package ghostpairprogrammingplugin;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.debug.core.IJavaExceptionBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;

import as_is_prog.ukagaka.UkagakaSSTPConnection;
import org.eclipse.jdt.internal.debug.ui.JavaDebugOptionsManager;

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
			
			System.err.println(jebp.getExceptionTypeName());	
			System.err.println(jebp.getClass().getName());
			System.err.println(arg0.getTopStackFrame().getLineNumber());			
			
			try {
				new UkagakaSSTPConnection("test").sendNotify1_1("OnExceptionOccured", jebp.getExceptionTypeName());
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
