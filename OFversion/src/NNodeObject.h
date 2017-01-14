#pragma once
#include "NBase.h"

struct NPoint;
struct NUI;
class NNodeObject : public NBaseObject {
public:
	NUI*ui;
	int step = -5;
	

	~NNodeObject();
	NNodeObject(int i = 0, int o = 0, int w = 200, int h = 100, int x = 0, int y = 0);
	//virtual void setup()=0;
	//virtual void close()=0 ;
	//virtual void drawUI();
	virtual bool calculate();
	virtual bool continueable() { return false; };
	bool calculatable();
	int getNumOfInput();
	int getNumOfOutput();
	NPoint* getInpt(const int);
	NPoint* getOutpt(const int);
	bool inOK();
	bool send();
	void cleanOut();
	void cleanIn();
	void setDelay(int d) { curDelay = delay = d; }
	void setTimes(int i) { timesLimit = nowTimes = i; }
protected:
	NPoint* inpt;//Input points
	NPoint* outpt;//Output points
	int timesLimit = -1;
	int delay = 0;
	int curDelay = 0;
	int nowTimes = 0;
	int width;
	int height;
	int numOfInput;
	int numOfOutput;
	void* globalData;//Data for special usage.
};