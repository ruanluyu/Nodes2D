#pragma once
#include "NBase.h"
#include "NNodeObject.h"
class NGenerator : public NNodeObject {
	NGenerator(int times = 1, int delay = 0, int in = 1) {
		NNodeObject(0,in);
		setTimes(times);
		setDelay(delay);
	}
	bool continuable()
	{
		if (timesLimit < 0)return true;
		if (nowTimes > 0) return true;
		return false;
	}
};