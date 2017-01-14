#include "NSystem.h"

#include <iostream>
using namespace std;


NLine* NSystem::connect(NPoint* p1, NPoint* p2)
{
	NLine *li = new NLine();
	p1->line = li;
	p2->line = li;
	return li;
}

bool NSystem::continuable() {
	for (int i = 0; i < list->getSize(); i++)
	{
		if ((reinterpret_cast<NNodeObject*>(list->getNext()))->continueable())
			return true;
	}
	return false;
}

void NSystem::run()
{
	for (int i = 0; i < list->getSize(); i++)
	{
		(reinterpret_cast<NNodeObject*>(list->getNext()))->step = -5;
	}
	running = true;
	bool curRunning = true;
	bool firstIn = true;
	NNodeObject *curNode;
	while (running)
	{
		step++;
		firstIn = true;
		cout << "-------Step " << step<<" -------" << endl;
		while (curRunning)
		{
			curRunning = false;
			list->goToStart();
			list->goToLast();
			for (int i = 0; i < list->getSize(); i++)
			{
				curNode = reinterpret_cast<NNodeObject*>(list->getNext());
				if (curNode->step != step)
				{
					if (curNode->inOK())
					{
						curNode->step = step;
						if (curNode->calculatable()&& curNode->calculate())
						{
							curRunning = true;
							curNode->send();
						}
					}
				}
			}
			if (firstIn)
			{
				if (!curRunning)
				{
					running = continuable();
					break;
				}
				else firstIn = false;
			}
		}
		for (int i = 0; i < list->getSize(); i++)
		{
			(reinterpret_cast<NNodeObject*>(list->getNext()))->step = step;
		}
		cout << "-------One Step Over-------" << endl << endl;
	}
	cout << endl << endl << "=======All Over after " << step << " step(s)=======" << endl << endl;
}









