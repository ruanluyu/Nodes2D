#pragma once
#include "NBase.h"
#include "NNodeObject.h"
#include <thread>


class NSystem {
public:
	NSystem():running(false), step(0){};
	~NSystem() { delete list; }
	void addNode(NNodeObject* ptr) { list->add(ptr); }
	NLine* connect(NPoint*, NPoint*);
	void removeNode(NNodeObject* ptr) { list->remove(ptr); }
	void run();
	void runThread() { trd = std::thread(&NSystem::run, this); }
	void stop() { running = false; };
	void clear() { list->clear(); }
	bool continuable();
private:
	std::thread trd;
	long step;
	bool running;
	NList *list;
};