#include "NNodeObject.h"
#include "NPoint.h"
#include <stdlib.h>

NNodeObject::~NNodeObject()
{
	delete[] inpt;
	delete[] outpt;
}
NNodeObject::NNodeObject(  int i , int o , int w , int h , int x,int y ):
	numOfInput(i),numOfOutput(o)
{
	inpt = new NPoint[i];
	outpt = new NPoint[o];
	for (int c = 0; c < i; c++)
	{
		(inpt + sizeof(NPoint*)*c)->parent = this;
		(inpt + sizeof(NPoint*)*c)->id = c;
	}
	for (int c = 0; c < o; c++)
	{
		(outpt + sizeof(NPoint*)*c)->parent = this;
		(outpt + sizeof(NPoint*)*c)->id = c;
	}
	ui = new NUI();
	ui->posX = x;
	ui->posY = y;
	ui->width = w;
	ui->height = h;
}

bool NNodeObject::calculate()
{
	return false;
}

bool NNodeObject::calculatable()
{
	if (!inOK())
		return false;
	if (delay != 0)
	{
		if (curDelay > 0)
		{
			curDelay--;
			return false;
		}
		else {
			curDelay = delay;
		}
	}
	if (timesLimit != -1)
	{
		if (nowTimes <= 0)
		{
			cleanIn();
			return false;
		}
		else
			nowTimes--;
	}
}

int NNodeObject::getNumOfInput() {
	return numOfInput;
};
int NNodeObject::getNumOfOutput() {
	return numOfOutput;
};
NPoint* NNodeObject::getInpt(const int id) {
	if(id>=numOfInput)
		return nullptr;
	return inpt + sizeof(NPoint*)*id;
};
NPoint *NNodeObject::getOutpt(const int id) {
	if (id >= numOfOutput)
		return nullptr;
	return outpt + sizeof(NPoint*)*id;
};



bool NNodeObject::inOK()
{
	NPoint *cur;
	for (int i = 0; i < numOfInput; i++)
	{
		cur = inpt + sizeof(NPoint*)*i;
		if (cur->data == nullptr)
		{
			return false;
		}
	}
	return true;
}

bool NNodeObject::send() 
{
	bool out = false;
	NPoint* cur;
	for (int i = 0; i < numOfInput; i++)
	{
		cur = outpt + sizeof(NPoint*)*i;
		if (cur->line!=nullptr && cur->data != nullptr)
		{
			out = true;
			(reinterpret_cast<NPoint*>((reinterpret_cast<NLine*>(cur->line))->to))->data = cur->data;
			cur->data = nullptr;
		}
	}
	cleanOut();
	return out;
}

void NNodeObject::cleanIn()
{
	for (int i = numOfInput-1; i >= 0; i--)
	{
		delete((inpt + sizeof(NPoint*)*i)->data);
	}
}

void NNodeObject::cleanOut()
{
	for (int i = numOfOutput - 1; i >= 0; i--)
	{
		delete((outpt + sizeof(NPoint*)*i)->data);
	}
}