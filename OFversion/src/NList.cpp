
#include "NList.h"
#include <iostream>
 
NList::NList():size(0), start(nullptr),end(nullptr)
{
	std::cout << "NList : Initialized" << std::endl;
}
NList::~NList()
{
	clear();
	std::cout << "NList : Deleted" << std::endl;
}
void NList::add( void* ptr) 
{
	if (size == 0) {
		start = end = cur = new NLData();
		cur->data = ptr;
		cur->last = cur;
		cur->next = cur;
	}
	else {
		cur = new NLData();
		cur->data = ptr;
		cur->last = end;
		cur->next = start;
		start->last = end;
		end->next = cur;
		end = cur;
	}
	size++;
	if (size<100)
		std::cout << "NList : Element(s) rised to "<<size<< std::endl;
}
void* NList::getCur()
{
	if (size == 0)return nullptr;
	return cur->data;
}
void* NList::getNext()
{
	if (size == 0)return nullptr;
	cur = cur->next;
	return cur->data;
}

void NList::remove(void * ptr)
{
	if (size == 0)return;
	if (size == 1) {
		if (cur->data == ptr)
		{
			delete (cur->data);
			delete cur;
			start=end = nullptr;
			size = 0;
			std::cout << "NList : Elements cleared" << std::endl;
		}
		return;
	}
	if (start->data == ptr) {
		cur = start;
		start = start->next;
		cutOut(cur);
	}
	else if (end->data == ptr) {
		cur = end;
		end = end->last;
		cutOut(cur);
	}
	else {
		for (int i = 0; i < size; i++) {
			if (cur->data == ptr) {
				cutOut(cur);
				cur = start;
				break;
			}
			cur = cur->next;
		}
	}
	
}
void NList::cutOut(NLData *ptr) {
	if (ptr == nullptr)return;
	ptr->next->last = ptr->last;
	ptr->last->next = ptr->next;
	ptr->next = ptr->last = nullptr;
	if (ptr->data != nullptr)delete (ptr->data);
	delete ptr;
	size--;
	std::cout << "NList : Removed an element, and " << size << " element(s) left." << std::endl;
}
void NList::clear()
{
	if (size == 0)return;
	start = nullptr;
	cur = end;
	while (size > 1) {
		end = cur->last;
		delete (cur->data);
		delete cur;
		cur = end;
		size--;
	}
	size = 0;
	delete end;
	end = cur = nullptr;
	std::cout << "NList : Elements cleared" << std::endl;
}