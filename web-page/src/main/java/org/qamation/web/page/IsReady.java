package org.qamation.web.page;

import org.openqa.selenium.support.ui.ExpectedCondition;

public interface IsReady {
	public boolean isReady();
	public boolean isReady(ExpectedCondition condition);
}
