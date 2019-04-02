#include <iostream>
#include <vector>
#include <algorithm>

bool sort_(int i, int j)
{ 
	return (i>j);
}

int main(int argc, char* argv[])
{
	int Coin_value;
	std::vector<int> vec;
	int N_value,N;
	int count = 0;
	int i = 0;
	int temp;
	while (std::cin >> Coin_value)
	{
		vec.push_back(Coin_value);
		if (getchar() == '\n')
		{
			break;
		}
	}
	temp = vec.size();
	std::cin >> N_value;
	N = N_value;
	std::sort(vec.begin(), vec.end(), sort_);
	while ((i < temp) && (N_value > 0))
	{
		if ((N_value / vec[i])>0)
		{
			count += N_value / vec[i];
			for (int j = 0; j < N_value / vec[i]; j++)
			{
				vec.push_back(vec[i]);
			}
			N_value -= vec[i] * (N_value / vec[i]);
		}
		i++;
	}
	std::cout << count<< std::endl;
	std::cout << N << " = " <<;
	i = temp;
	for (; i < vec.size(); i++)
	{
		if (i == vec.size() - 1)
		{
			std::cout << vec[i] << std::endl;
		}
		else
		{
			std::cout << vec[i] << "+";
		}
	}
	system("pause");
	return 0;
}